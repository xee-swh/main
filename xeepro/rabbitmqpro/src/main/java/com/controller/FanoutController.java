package com.controller;

import com.config.FanoutExchangeConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fanout")
public class FanoutController {
 
    private final RabbitTemplate rabbitTemplate;
 
    public FanoutController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
 
    /**
     * fanout交换机为扇形模式交换机
     *      消息会发送到所有绑定的队列上。
     *
     * @return
     */
    @GetMapping("send")
    public Object sendMsg() {
        rabbitTemplate.convertAndSend(FanoutExchangeConfig.FANOUT_EXCHANGE, null, "发送一条测试消息：fanout");
        return "fanout消息发送成功！！";
    }
}