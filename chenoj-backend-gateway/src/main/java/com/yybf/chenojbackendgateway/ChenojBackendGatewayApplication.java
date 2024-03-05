package com.yybf.chenojbackendgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
// 因为引入的有mybatisplus的依赖，所以启动的时候默认开启一个数据库，会报错，所以直接排除掉数据源自动配置
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class ChenojBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChenojBackendGatewayApplication.class, args);
    }

}
