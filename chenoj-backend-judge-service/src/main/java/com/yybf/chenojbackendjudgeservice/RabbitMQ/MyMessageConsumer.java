package com.yybf.chenojbackendjudgeservice.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.yybf.chenojbackendcommon.common.ErrorCode;
import com.yybf.chenojbackendcommon.exception.BusinessException;
import com.yybf.chenojbackendjudgeservice.judge.JudgeService;
import com.yybf.chenojbackendmodel.entity.Question;
import com.yybf.chenojbackendmodel.entity.QuestionSubmit;
import com.yybf.chenojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.yybf.chenojbackendserviceclient.service.QuestionFeignClient;
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
public class MyMessageConsumer {

    @Resource
    private JudgeService judgeService;

    @Resource
    private QuestionFeignClient questionFeignClient;

    /**
     * @return null:
     * @author yangyibufeng
     * @description 向消息队列获取消息（消费者端）
     * @date 2024/3/6 21:23
     */
    // 指定程序监听的消息队列和确认机制
    @SneakyThrows
    @RabbitListener(queues = {"code_queue"}, ackMode = "MANUAL") // MANUAL 表示从消息队列中拿到消息后需要手动确认收到（可操作性强）
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        long questionSubmitId = Long.parseLong(message);

        if(message == null){
            // 如果消息为空，则不进行处理，直接抛出异常
            throw new BusinessException(ErrorCode.NULL_ERROR, "消息为空");
        }
        // 开始统计题目的通过数
        try {
            judgeService.doJudge(questionSubmitId);

            // 获取提交的题目信息
            QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);

            // 如果题目没有通过
            if(!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.SUCCEED.getValue())){
                // 直接报错,抛出异常
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "答案未通过");
            }

            // 如果答案通过,开始更新题目的通过数
            log.info("更新题目通过数,提交的信息:" + message);

            //获取题目id
            Long questionId = questionSubmit.getQuestionId();
            log.info("题目Id = {}", questionId);

            // 获取对应的题目
            Question question = questionFeignClient.getQuestionById(questionId);

            // 开始对question加锁,进行通过数的更新
            synchronized (question){
                // 获取对应题目的通过数
                Integer acceptedNum = question.getAcceptedNum();

                // 构建更新数据
                Question updateQuestion = new Question();
                updateQuestion.setId(questionId);
                updateQuestion.setAcceptedNum(acceptedNum + 1);


                // 更新题目的通过数
                boolean updated = questionFeignClient.updateQuestionById(updateQuestion);
                if(!updated){
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新题目通过数失败");
                }
            }

            // 手动确认消息
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            channel.basicNack(deliveryTag,false,false); // 最后一个true：表示接收失败后重新处理
            throw new RuntimeException(e);
        }
    }

}
