package com.yybf.chenojbackendquestionservice.RabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yangyibufeng
 * @date 2024/3/6
 */
@Component
public class MyMessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * @param exchange: 目标交换机
     * @param routingKey: 消息标识
     * @param message: 消息内容
     * @return void:
     * @author yangyibufeng
     * @description 向消息队列发送消息（生产者端）
     * @date 2024/3/6 20:59
     */
    public void sendMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

}
