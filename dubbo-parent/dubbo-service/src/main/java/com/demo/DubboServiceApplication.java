package com.demo;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableDubbo(scanBasePackages = "com.demo.service")
@ImportResource(value = "classpath:provider-service.xml")
@SpringBootApplication
@MapperScan("com.demo.dao")
public class DubboServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceApplication.class, args);
    }

}
