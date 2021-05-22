package com.szw.me.mmd.service.impl;

import com.szw.me.framework.util.JwtUtil;
import com.szw.me.framework.util.ResultVOUtil;
import com.szw.me.framework.vo.ResultVO;
import com.szw.me.mmd.domain.User;
import com.szw.me.mmd.mapper.UserMapper;
import com.szw.me.mmd.repository.UserRepository;
import com.szw.me.mmd.req.RegisterReq;
import com.szw.me.mmd.service.UserCodeService;
import com.szw.me.mmd.service.UserService;
import com.szw.me.mmd.util.MD5Util;
import com.szw.me.mmd.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

@EnableAsync
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserCodeService userCodeService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO register(RegisterReq req) {
        User existedUser = userMapper.selectByNick(req.getNick());
        if (existedUser != null) {
            return ResultVOUtil.error("昵称已存在");
        }
        User user = new User();
        BeanUtils.copyProperties(req, user, "password");
        user.setPassword(MD5Util.stringMD5(req.getPassword()));
        user.setCode(userCodeService.getNextCode(getPrefix()));
        user.setCtime(new Date());
        user.setMtime(new Date());
        userMapper.insert(user);
        userRepository.save(user);
        log.info("user registered: [{}]", user);
        return ResultVOUtil.success(user);
    }

    @Override
    public ResultVO loginByNick(String nick, String password) {
        User user = userMapper.selectByNick(nick);
        if (user == null) {
            return ResultVOUtil.error("用户不存在");
        }
        if (user.getPassword().equals(MD5Util.stringMD5(password))) {
            // 返回token
            String token = JwtUtil.sign(user.getCode(), user.getNick());
            return ResultVOUtil.success(token);
        }
        return ResultVOUtil.error("密码错误");
    }

    @Override
    public ResultVO getUser(String code) {
        if (code == null) {
            return ResultVOUtil.error("请给出查询的code");
        }
        User user;
        String key = RedisUtil.wrapperUserKey(code);
        Object userObj = redisTemplate.opsForValue().get(key);
        if (userObj != null) {
            user = (User) userObj;
            log.info("getUser from redis: [{}]", user);
        } else {
            user = userMapper.selectByCode(code);
            redisTemplate.opsForValue().set(key, user);
            log.info("getUser from mysql: [{}]", user);
        }
        return ResultVOUtil.success(user);
    }

    @Override
    public ResultVO findByNick(String nick) {
        List<SearchHit<User>> hits = userRepository.findByNick(nick);
        return ResultVOUtil.success(hits);
    }

    @Override
    public ResultVO esUser() {
        List<User> userList = new ArrayList<>();
        Iterator<User> iterator = userRepository.findAll().iterator();
        while (iterator.hasNext()) {
            userList.add(iterator.next());
        }
        return ResultVOUtil.success(userList);
    }

    @Async("taskExecutor")
    @Override
    public void testAsync1() {
        for (int i=0; i<10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("=== current i: " + i + ", === current thread: " + Thread.currentThread().getName());
        }
    }

    @Async("szwTaskExecutor")
    @Override
    public void testAsync2() {
        for (int i=0; i<10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("=== current i: " + i + ", === current thread: " + Thread.currentThread().getName());
        }
    }

    @Override
    public void send() {
        rabbitTemplate.convertAndSend("directExchange", "message", "一条消息");
    }

    @Override
    @RabbitHandler
    @RabbitListener(queues = "messageQueue")
    public void receive(String message) {
        System.out.println("接收到了消息" + message);
    }

    @Override
    public void sendNews() {
        rabbitTemplate.convertAndSend("topicExchange", "nj.news", "第一条消息", new CorrelationData("1234567890"));
    }

    @Override
    @RabbitHandler
    @RabbitListener(queues = "njQueue")
    public void receiveNj(String message) {
        System.out.println("南京队列接收到了消息" + message);
    }

    @Override
    @RabbitHandler
    @RabbitListener(queues = "newsQueue")
    public void receiveNews(String message) {
        System.out.println("新闻队列接收到了消息" + message);
    }

    @PostConstruct
    public void setConfirmCallback() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("因为：" + cause + "，导致消息：" + correlationData.getId() + "投递失败");
            }
        });
    }
    private String getPrefix() {
        return String.valueOf(new Random().nextInt(6));
    }
}
