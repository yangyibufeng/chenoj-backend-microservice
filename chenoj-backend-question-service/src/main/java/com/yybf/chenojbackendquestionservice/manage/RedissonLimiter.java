package com.yybf.chenojbackendquestionservice.manage;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 提供redisson限流服务
 *
 * @author yangyibufeng
 * @date 2024/7/14
 */
@Service
@Slf4j
public class RedissonLimiter {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 限流操作
     *
     * @param key: 根据key采取不同的限流策略
     * @return boolean:
     * @author yangyibufeng
     * @date 2024/7/14 8:47
     */
    public boolean doRateLimit(String key) {
        log.info("key = {}", key);
        // 获取分布式限流对象 -- 创建一个限流器
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        // 设置限流策略
        // 每秒最多访问 2 次
        // 参数1 type：限流类型，可以是自定义的任何类型，用于区分不同的限流策略。
        // 参数2 rate：限流速率，即单位时间内允许通过的请求数量。
        // 参数3 rateInterval：限流时间间隔，即限流速率的计算周期长度。
        // 参数4 unit：限流时间间隔单位，可以是秒、毫秒等。
        boolean trySetRate = rateLimiter.trySetRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS);

        // 成功打印日志 -- 获取限流速率和时间间隔
        if (trySetRate) {
            log.info("init rate = {}, interval = {}", rateLimiter.getConfig().getRate(),
                    rateLimiter.getConfig().getRateInterval());
        }

        // 每当一个操作来了后，请求一个令牌
        return rateLimiter.tryAcquire(1);
    }
}