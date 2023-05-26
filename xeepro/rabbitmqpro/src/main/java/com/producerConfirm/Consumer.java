package com.producerConfirm;

import com.config.RabbitConfig;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class Consumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(RabbitConfig.QUEUE_CONFIRM_1),
                    exchange = @Exchange(value = RabbitConfig.DIRECT_EXCHANGE_CONFIRM),
                    key = {RabbitConfig.ROUTE_KEY1})})
    public void receive(String msg) {
        LOGGER.info("receive: {}", msg);
    }


    /**
     * 消费端如果没有单独设置的话默认就是MQ不管理。换句话说MQ只负责发送消息。
     * mq为我们提供了三种模式 NONE, MANUAL, AUTO;
     *
     * 默认的我们需要手动将连接工厂设置MANUAL后再接收到消息后我们需要手动确认，mq才会删除消息。
     * 否则会一直等待到消费端重启才会进行重新分发数据
     *
     * channel.basicAck(long,boolean); 确认收到消息，消息将被队列移除，false只确认当前consumer一个消息收到，
     *     true确认所有consumer获得的消息。
     *
     * channel.basicNack(long,boolean,boolean); 确认否定消息，第一个boolean表示一个consumer还是所有，
     *     第二个boolean表示requeue是否重新回到队列，true重新入队。
     *
     * channel.basicReject(long,boolean); 拒绝消息，requeue=false 表示不再重新入队，
     *     如果配置了死信队列则进入死信队列。
     *
     *
     * @param msg 消息
     * @param channel 信道
     */
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(RabbitConfig.QUEUE_CONFIRM_1),
                    exchange = @Exchange(value = RabbitConfig.DIRECT_EXCHANGE_CONFIRM),
                    key = {RabbitConfig.ROUTE_KEY1})})
    public void receive(String msg, Channel channel) {
        LOGGER.info("receive: {}", msg);
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                try {
                    byte[] body = message.getBody();
                    String messageContent = new String(body, StandardCharsets.UTF_8);
                    if ("error".equals(messageContent)) {
                        throw new RuntimeException("业务异常");
                    }
                    LOGGER.info("收到的消息内容：{}", messageContent);
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } catch (Exception e) {
                    LOGGER.info("消费消息失败!重回队列!");
                    channel.basicNack(message.getEnvelope().getDeliveryTag(), false, true);
                }
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
                LOGGER.info("取消订阅：{}", consumerTag);
            }
        };
        try {
            channel.basicConsume("confirm.queue", false, deliverCallback, cancelCallback);
        } catch (IOException e) {
            LOGGER.info("exception：{}", e);
        }
    }

}