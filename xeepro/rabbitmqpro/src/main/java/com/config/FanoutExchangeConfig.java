package com.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * fanout:
 *  每个发到fanout类型交换机的消息都会分到所有绑定的队列上去。
 *  fanout交换器不处理路由键，只是简单的将队列绑定到交换机上，
 *  每个发送到交换机的消息都会被转发到与该交换机绑定的所有队列上。
 *
 *  类似子网广播，每台子网内的主机都获得了一份复制的消息。
 *
 *  fanout类型转发消息是最快的。
 *
 */
@Configuration
public class FanoutExchangeConfig {
 
    public static final String FANOUT_QUEUE = "fanoutQueue";
    public static final String FANOUT_QUEUE2 = "fanoutQueue2";
    public static final String FANOUT_QUEUE3 = "fanoutQueue3";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";
    public static final String FANOUT_ROUTING_KEY = "fanout";
 
    @Bean
    public Queue fanoutQueue() {
        return new Queue(FANOUT_QUEUE, true);
    }
 
    @Bean
    public Queue fanoutQueue2() {
        return new Queue(FANOUT_QUEUE2, true);
    }
 
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE, true, false);
    }
 
    @Bean
    public Binding bindingFanoutExchange(Queue fanoutQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue).to(fanoutExchange);
    }
 
    @Bean
    public Binding bindingFanoutExchange2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }
}