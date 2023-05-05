package com.controller;

import com.google.common.collect.Lists;
import com.util.KafkaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kafka")
@Api(tags = "Kafka控制器")
public class KafkaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private KafkaUtils kafkaUtils;

    /**
     * 新增topic (支持批量，这里就单个作为演示)
     *
     * @param topic topic
     */
    @ApiOperation("新增topic")
    @PostMapping()
    public String add(String topic) {
        LOGGER.info("add topic: " + topic);
        NewTopic newTopic = new NewTopic(topic, 3, (short) 1);
        kafkaUtils.createTopic(Lists.newArrayList(newTopic));
        return "success";
    }

    /**
     * 查询topic信息 (支持批量，这里就单个作为演示)
     *
     * @param topic 自增主键
     * @return ResponseVo
     */
    @ApiOperation("查询topic信息")
    @GetMapping("/{topic}")
    public String getBytTopic(@PathVariable String topic) {
        LOGGER.info("getBytTopic topic: " + topic);
        return kafkaUtils.getTopicInfo(Lists.newArrayList(topic));
    }

    /**
     * 删除topic (支持批量，这里就单个作为演示)
     * (注意：如果topic正在被监听会给人感觉删除不掉（但其实是删除掉后又会被创建）)
     *
     * @param topic topic
     * @return ResponseVo
     */
    @ApiOperation("删除topic")
    @DeleteMapping("/{topic}")
    public String delete(@PathVariable String topic) {
        LOGGER.info("delete topic: " + topic);
        kafkaUtils.deleteTopic(Lists.newArrayList(topic));
        return "success";
    }

    /**
     * 查询所有topic
     *
     * @return ResponseVo
     */
    @ApiOperation("查询所有topic")
    @GetMapping("/allTopic")
    public List<String> getAllTopic() {
        return kafkaUtils.getAllTopic();
    }

    /**
     * 生产者往topic中发送消息demo
     *
     * @param topic
     * @param message
     * @return
     */
    @ApiOperation("往topic发送消息")
    @PostMapping("/message")
    public String sendMessage(String topic, String message) {
        kafkaUtils.sendMessage(topic, message);
        return "success";
    }

    /**
     * 消费者示例demo
     * <p>
     * 基于注解监听多个topic，消费topic中消息
     * （注意：如果监听的topic不存在则会自动创建）
     */
    @KafkaListener(topics = {"topic1", "topic2", "topic3"})
    public void consume(String message) {
        LOGGER.info("receive msg: " + message);
    }
}