package com.yybf.chenojbackendjudgeservice.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.yybf.chenojbackendjudgeservice.judge.JudgeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author yangyibufeng
 * @date 2024/3/6
 */
@Component
@Slf4j
public class MyMessageProducer {

    @Resource
    private JudgeService judgeService;

    /**
     * @return null:
     * @author yangyibufeng
     * @description 向消息队列发送消息（消费者端）
     * @date 2024/3/6 21:23
     */
    // 指定程序监听的消息队列和确认机制
    @SneakyThrows
    @RabbitListener(queues = {"code_queue"}, ackMode = "MANUAL") // MANUAL 表示从消息队列中拿到消息后需要手动确认收到（可操作性强）
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        long questionSubmitId = Long.parseLong(message);
        try {
            judgeService.doJudge(questionSubmitId);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            channel.basicNack(deliveryTag,false,false); // 最后一个true：表示接收失败后重新处理
        }
    }

}
