package com.szw.me.mmd.controller;

import com.szw.me.framework.util.ResultVOUtil;
import com.szw.me.framework.vo.ResultVO;
import com.szw.me.mmd.req.RegisterReq;
import com.szw.me.mmd.service.UserService;
import com.szw.me.mmd.util.SpringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public ResultVO register(@RequestBody @Validated RegisterReq req) {
        return userService.register(req);
    }

    @PostMapping("/login/nick")
    @ApiOperation("用户登陆 - nick")
    public ResultVO login(@RequestParam("nick") String nick, @RequestParam("password") String password) {
        return userService.loginByNick(nick, password);
    }

    @GetMapping("/{code}")
    @ApiOperation("根据code获取用户信息")
    public ResultVO get(@PathVariable("code") String code) {
        return userService.getUser(code);
    }

    @GetMapping
    @ApiOperation("查询用户昵称")
    public ResultVO find(@RequestParam("nick") String nick) {
        return userService.findByNick(nick);
    }

    @GetMapping("/test")
    @ApiOperation("测试异步调用")
    public ResultVO testAsync() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) SpringUtil.getBean("szwTaskExecutor");
        System.out.println(taskExecutor.getThreadNamePrefix());
        userService.testAsync1();
        userService.testAsync1();
        userService.testAsync2();
        userService.testAsync2();
        return ResultVOUtil.success();
    }

    @GetMapping("/send")
    @ApiOperation("测试rabbitmq")
    public ResultVO send() {
        userService.sendNews();
        return ResultVOUtil.success();
    }
}
