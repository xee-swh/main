package com.subcribe;

import com.config.DirectExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *
 * Direct消费
 *
 * 尽管设置了两个消费者，但是只有一个能够消费成功
 *
 * 多次发送则轮训消费：
 *  DirectReceiver消费者收到消息1  : 发送一条测试消息：direct
 *  DirectReceiver消费者收到消息2  : 发送一条测试消息：direct
 *  DirectReceiver消费者收到消息1  : 发送一条测试消息：direct
 *  DirectReceiver消费者收到消息2  : 发送一条测试消息：direct
 *
 * 一个交换机可以绑定多个队列。如果通过路由key可以匹配到多个队列，消费的时候同一个队列（一个相同的队列监听了多次）也只能有一个进行消费
 *
 * 由于我们又两个队列都绑定了交换机，且routeKey一样，所以会打印两条。
 * 要注意direct只有routeKey完全匹配的时候才能被消费，同时每个队列中的消息只会被消费一次。
 */
@Slf4j
@Component
public class DirectQueueListener {
    @RabbitHandler
    @RabbitListener(queues = DirectExchangeConfig.DIRECT_QUEUE)
    public void process(String testMessage) {
        System.out.println("DirectReceiver消费者收到消息1  : " + testMessage);
    }
 
    @RabbitHandler
    @RabbitListener(queues = DirectExchangeConfig.DIRECT_QUEUE)
    public void process2(String testMessage) {
        System.out.println("DirectReceiver消费者收到消息2  : " + testMessage);
    }
 
    @RabbitHandler
    @RabbitListener(queues = DirectExchangeConfig.DIRECT_QUEUE2)
    public void process3(String testMessage) {
        System.out.println("DirectReceiver消费者收到消息3  : " + testMessage);
    }
 
}