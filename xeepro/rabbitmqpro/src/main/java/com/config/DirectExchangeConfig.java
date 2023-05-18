package com.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Direct交换机
 *
 * 声明Direct交换机，队列和绑定关系。
 *
 *  消息中的路由键（routing key）如果和Binding中的bing key一致，交换机就将消息发送到队列的队列中。路由键要完全匹配，单个传播。
 *
 */
@Configuration
public class DirectExchangeConfig {

    public static final String DIRECT_QUEUE = "directQueue";
    public static final String DIRECT_QUEUE2 = "directQueue2";
    public static final String DIRECT_EXCHANGE = "directExchange";
    public static final String DIRECT_ROUTING_KEY = "direct";

    // 创建队列
    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE, true);
    }

    // 创建队列
    @Bean
    public Queue directQueue2() {
        return new Queue(DIRECT_QUEUE2, true);
    }

    // 创建交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE, true, false);
    }

    // 绑定队列和交换机
    @Bean
    public Binding bindingDirectExchange(Queue directQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue).to(directExchange).with(DIRECT_ROUTING_KEY);
    }

    // 绑定队列和交换机
    @Bean
    public Binding bindingDirectExchange2(Queue directQueue2, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue2).to(directExchange).with(DIRECT_ROUTING_KEY);
    }

}
