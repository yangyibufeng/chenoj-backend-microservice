package com.yybf.chenojbackendgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ChenojBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChenojBackendGatewayApplication.class, args);
    }

}
