package com.yybf.chenojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.yybf.chenojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.yybf")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.yybf.chenojbackendserviceclient.service"})
public class ChenojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChenojBackendUserServiceApplication.class, args);
    }

}
