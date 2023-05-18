package com.subcribe;

import com.config.TopicExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * topic: 主题交换机
 *
 *
 */
@Slf4j
@Component
public class TopicQueueListener {
    @RabbitHandler
    @RabbitListener(queues = TopicExchangeConfig.TOPIC_QUEUE)
    public void process(String testMessage) {
        System.out.println("TopicReceiver消费者收到消息1  : " + testMessage);
    }
 
    @RabbitHandler
    @RabbitListener(queues = TopicExchangeConfig.TOPIC_QUEUE)
    public void process2(String testMessage) {
        System.out.println("TopicReceiver消费者收到消息2  : " + testMessage);
    }
 
    @RabbitHandler
    @RabbitListener(queues = TopicExchangeConfig.TOPIC_QUEUE2)
    public void process3(String testMessage) {
        System.out.println("TopicReceiver消费者收到消息3  : " + testMessage);
    }
 
    @RabbitHandler
    @RabbitListener(queues = TopicExchangeConfig.TOPIC_QUEUE3)
    public void process4(String testMessage) {
        System.out.println("TopicReceiver消费者收到消息4  : " + testMessage);
    }
 
}