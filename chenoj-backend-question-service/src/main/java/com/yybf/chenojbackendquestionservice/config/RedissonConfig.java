package com.yybf.chenojbackendquestionservice.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用来管理RedissonClient的配置
 *
 * @author yangyibufeng
 * @date 2024/7/14
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
@Configuration
@Slf4j
public class RedissonConfig {
    @Value("${spring.datasource.redis.database}")
    private Integer database;
    @Value("${spring.datasource.redis.host}")
    private String host;
    @Value("${spring.datasource.redis.port}")
    private Integer port;
//    @Value("${spring.datasource.redis.password}")
//    private String password;

    @Bean
    public RedissonClient redissonClient() {
        log.info("Starting Redisson client configuration...");
        Config config = new Config();
        config.useSingleServer()
                .setDatabase(database)
                .setAddress("redis://" + host + ":" + port);
//                .setPassword(password);
        log.debug("Redisson client configuration: {}", config); // 使用日志框架输出调试信息

        try {
            RedissonClient redisson = Redisson.create(config);
            log.info("Redisson client started successfully.");
            return redisson;
        } catch (Exception e) {
            log.error("Failed to start Redisson client.", e);
            throw new RuntimeException("Failed to start Redisson client", e); // 抛出自定义异常，便于问题定位
        }
    }
}