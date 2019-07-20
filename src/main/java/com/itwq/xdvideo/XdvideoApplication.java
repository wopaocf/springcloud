package com.itwq.xdvideo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.itwq.xdvideo.dao")
@EnableTransactionManagement//开启事务
public class XdvideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(XdvideoApplication.class, args);
    }

}
