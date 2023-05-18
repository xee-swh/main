package com.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * topic交换机通过模式匹配分配路由的路由键属性，将路由键和某个模式进行匹配，
 * 此时队列需要绑定到一个模式上。它将路由键和绑定键的字符串切分成单词，
 * 这些单词之间用.隔开。
 *
 * 它同样会识别两个通配符： # 和* 。 #匹配0个或多个单词， * 匹配一个单词
 *
 */
@Configuration
public class TopicExchangeConfig {
 
    public static final String TOPIC_QUEUE = "topicQueue";
    public static final String TOPIC_QUEUE2 = "topicQueue2";
    public static final String TOPIC_QUEUE3 = "topicQueue3";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String TOPIC_ROUTING_KEY = "topic*";
 
    @Bean
    public Queue topicQueue() {
        return new Queue(TOPIC_QUEUE, true);
    }
 
    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2, true);
    }
 
    @Bean
    public Queue topicQueue3() {
        return new Queue(TOPIC_QUEUE3, true);
    }
 
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, true, false);
    }
 
    @Bean
    public Binding bindingTopicExchange(Queue topicQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue).to(topicExchange).with("topic.#");
    }
 
    @Bean
    public Binding bindingTopicExchange2(Queue topicQueue2, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with("test.#");
    }
 
    @Bean
    public Binding bindingTopicExchange3(Queue topicQueue3, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue3).to(topicExchange).with("#");
    }
}