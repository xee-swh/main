package com.controller;

import com.config.TopicExchangeConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic")
public class TopicController {
 
    private final RabbitTemplate rabbitTemplate;
 
    public TopicController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
 
    @GetMapping("send")
    public Object sendMsg(String routingKey) {
        rabbitTemplate.convertAndSend(TopicExchangeConfig.TOPIC_EXCHANGE, routingKey, "发送一条测试消息：topic");
        return "topic消息发送成功！！";
    }
}