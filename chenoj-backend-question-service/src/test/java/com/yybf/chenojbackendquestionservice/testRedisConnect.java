package com.yybf.chenojbackendquestionservice;

import com.yybf.chenojbackendquestionservice.config.RedissonConfig;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * $END$
 * 测试通过配置文件进行redis的链接
 *
 * @author yangyibufeng
 * @date 2024/7/14
 */
public class testRedisConnect {
    @Resource
    RedissonConfig redisConfig;
    @Test
    public void testConnect() {
        redisConfig.redissonClient();
    }
}