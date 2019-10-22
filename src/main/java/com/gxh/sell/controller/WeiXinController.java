package com.gxh.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeiXinController {
    @GetMapping("/auth")
    public void auth(@RequestParam("code")String code){
        log.info("code={}",code);
        log.info("进入auth方法。。。");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxcdf4df9774db6673&redirect_uri&secret=a0da7779a6c19aecf52bd8aeb5dd7d94&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info(response);
    }
}
