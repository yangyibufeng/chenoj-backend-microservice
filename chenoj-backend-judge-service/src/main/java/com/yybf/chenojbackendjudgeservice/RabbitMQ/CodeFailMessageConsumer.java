package com.yybf.chenojbackendjudgeservice.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.yybf.chenojbackendcommon.common.ErrorCode;
import com.yybf.chenojbackendcommon.exception.BusinessException;
import com.yybf.chenojbackendmodel.entity.QuestionSubmit;
import com.yybf.chenojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.yybf.chenojbackendserviceclient.service.QuestionFeignClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.yybf.chenojbackendcommon.constant.MqConstant.CODE_DLX_QUEUE;

/**
 * 用来监听死信队列
 *
 * @author yangyibufeng
 * @date 2024/7/13
 */
@Component
@Slf4j
public class CodeFailMessageConsumer {

    @Resource
    private QuestionFeignClient questionFeignClient;

    /**
     * 监听死信队列
     *
     * @param message
     * @param channel
     * @param dekivery
     */
    @SneakyThrows
    @RabbitListener(queues = {CODE_DLX_QUEUE}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long dekivery) {
        // 接收到失败的信息
        log.info("死信队列接受到的消息：{}", message);
        if (StringUtils.isBlank(message)) {
            channel.basicNack(dekivery, false, false);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "消息为空");
        }
        long questionSubmitId = Long.parseLong(message);
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            channel.basicNack(dekivery, false, false);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "提交的题目信息不存在");
        }
        // 把提交题目标为失败
        questionSubmit.setStatus(QuestionSubmitStatusEnum.FAILED.getValue());

        boolean update = questionFeignClient.updateQuestionSubmit(questionSubmit);
        if (!update) {
            log.info("处理死信队列消息失败,对应提交的题目id为:{}", questionSubmit.getId());
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "处理死信队列消息失败");
        }
        // 确认消息
        channel.basicAck(dekivery, false);

    }
}