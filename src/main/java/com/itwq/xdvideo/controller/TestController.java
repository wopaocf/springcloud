package com.itwq.xdvideo.controller;


import com.itwq.xdvideo.config.WeChatConfig;
import com.itwq.xdvideo.dao.VideoMapper;
import com.itwq.xdvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VideoService videoService;

    @GetMapping("/test")
    public String testConfig(){
        return weChatConfig.getMpAppId();
    }
    @GetMapping("/testdb")
    public Object testDB(){
        return videoMapper.findAll();
    }
}
