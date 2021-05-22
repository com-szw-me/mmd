package com.szw.me.mmd.client;

import com.szw.me.framework.feign.FeignConfiguration;
import com.szw.me.framework.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "mmd", configuration = FeignConfiguration.class)
@RequestMapping("/mmd/user")
public interface UserClient {
    @GetMapping("/{code}")
    ResultVO get(@PathVariable("code") String code);
}