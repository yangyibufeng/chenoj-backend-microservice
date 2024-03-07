package com.yybf.chenojbackendjudgeservice.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 用于创建测试程序用到的交换机和队列（只用在程序启动前执行一次）
 *
 * @author yangyibufeng
 * @date 2024/3/6
 */
@Slf4j
public class InitRabbitMQ {

    public static void doInit() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            // 和消息队列建立连接
            Connection connection = factory.newConnection();
            // 一个操作消息队列的客户端，类似JDBC，RedisClient，DockerClient
            Channel channel = connection.createChannel();
            String EXCHANGE_NAME = "code_exchange";
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // 创建队列，随机分配一个队列名称
            String queueName = "code_queue";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, "my_routingKey");
            log.info("消息队列启动成功");
        } catch (Exception e) {
            log.error("消息队列启动失败" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        doInit();
    }

}