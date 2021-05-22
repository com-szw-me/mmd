package com.szw.me.mmd.service;

import com.szw.me.framework.vo.ResultVO;
import com.szw.me.mmd.req.RegisterReq;

public interface UserService {
    ResultVO register(RegisterReq req);
    ResultVO loginByNick(String nick, String password);
    ResultVO getUser(String code);
    ResultVO findByNick(String nick);
    ResultVO esUser();
    void testAsync1();
    void testAsync2();
    void send();
    void receive(String message);
    void sendNews();
    void receiveNj(String message);
    void receiveNews(String message);
}
