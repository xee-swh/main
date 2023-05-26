package com.controller;

import com.config.DirectExchangeConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direct")
public class DirectController {
 
    private final RabbitTemplate rabbitTemplate;
 
    public DirectController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
 
    /**
     * direct交换机为直连模式交换机
     *      根据消息携带的路由键将消息投递给对应队列
     * @return
     */
    @GetMapping("send")
    public Object sendMsg() {
        rabbitTemplate.convertAndSend(DirectExchangeConfig.DIRECT_EXCHANGE, DirectExchangeConfig.DIRECT_ROUTING_KEY, "发送一条测试消息：direct");
        return "direct消息发送成功！！";
    }

}