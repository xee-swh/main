package com.controller;

import com.producerConfirm.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rabbit")
public class RabbitController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitController.class);

    @Autowired
    private Producer producer;

    @GetMapping("send")
    public Object send(@RequestParam(value = "msg") String msg) {
        LOGGER.info("msg: " + msg);
        return producer.send(msg);
    }

    @GetMapping("sendBatch")
    public void sendBatch(@RequestParam(value = "param") List<String> msgs) {
        LOGGER.info("msgs: " + msgs);
        producer.sendBatch(msgs);
    }

    @GetMapping("sendSync")
    public void sendSync(@RequestParam(value = "msg") String msg) {
        LOGGER.info("msgs: " + msg);
        producer.sendSync(msg);
    }
}