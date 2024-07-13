package com.yybf.chenojbackendcommon.constant;

/**
 * 用来存储有关MQ的常量
 *
 * @author yangyibufeng
 * @date 2024/7/13 12:24
 */
public interface MqConstant {

    /**
     * 普通交换机
     */
    String CODE_EXCHANGE_NAME = "code_exchange";
    String CODE_QUEUE = "code_queue";
    String CODE_ROUTING_KEY = "code_routingKey";
    String CODE_DIRECT_EXCHANGE = "direct";

    /**
     * 死信队列交换机
     */
    String CODE_DLX_EXCHANGE = "code-dlx-exchange";

    /**
     * 死信队列
     */
    String CODE_DLX_QUEUE = "code_dlx_queue";

    /**
     * 死信队列路由键
     */
    String CODE_DLX_ROUTING_KEY = "code_dlx_routingKey";
}
