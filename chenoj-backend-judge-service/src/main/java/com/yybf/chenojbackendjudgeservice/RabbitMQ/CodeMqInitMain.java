package com.yybf.chenojbackendjudgeservice.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.yybf.chenojbackendcommon.constant.MqConstant.*;

/**
 * 主要为CodeQueue队列绑定了死信队列和死信交换机
 */
@Slf4j
public class CodeMqInitMain {

    public static void doInit() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            // 和消息队列建立连接
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            String codeExchangeName = CODE_EXCHANGE_NAME;
            channel.exchangeDeclare(codeExchangeName, CODE_DIRECT_EXCHANGE);

            // 创建 code 队列
            String codeQueue = CODE_QUEUE;
            Map<String, Object> codeMap = new HashMap<>();

            // code队列绑定死信交换机
            codeMap.put("x-dead-letter-exchange", CODE_DLX_EXCHANGE);
            codeMap.put("x-dead-letter-routing-key", CODE_DLX_ROUTING_KEY);
            channel.queueDeclare(codeQueue, true, false, false, codeMap);
            channel.queueBind(codeQueue, codeExchangeName, CODE_ROUTING_KEY);

            // 创建死信队列和死信交换机
            // 创建死信队列
            channel.queueDeclare(CODE_DLX_QUEUE, true, false, false, null);
            // 创建死信交换机
            channel.exchangeDeclare(CODE_DLX_EXCHANGE, CODE_DIRECT_EXCHANGE);
            channel.queueBind(CODE_DLX_QUEUE, CODE_DLX_EXCHANGE, CODE_DLX_ROUTING_KEY);

            log.info("消息队列启动成功！");
        } catch (Exception e) {
            log.error("消息队列启动失败");
            e.printStackTrace();
        }
        return;
    }

    public static void main(String[] args) {
        doInit();
        return ;
    }

}