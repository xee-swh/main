package com.producerConfirm;

import com.config.RabbitConfig;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * 开启确认模式的生产者
 */
@Component
public class Producer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * 发送消息
     *
     * @param msg 单条消息
     * @return 发送结果信息
     */
    public String send(String msg) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();
        ) {
            // channel.confirmSelect()方法开启当前信道上的confirm机制。
            channel.confirmSelect();

            // 发送消息
            channel.basicPublish(RabbitConfig.DIRECT_EXCHANGE_CONFIRM, RabbitConfig.ROUTE_KEY1, null, msg.getBytes());

            // waitForConfirms方法会阻塞，直到自上次调用以来发布的所有消息都被broker反馈了ack或者nack。
            boolean ack = channel.waitForConfirms();

            // 此时消费者是否成功消费，无法知道，即使消费失败了，也可能这里是success
            if (ack) {
                LOGGER.info("success");
                return "success";
            } else {
                LOGGER.info("failed");
                return "failed";
            }
        } catch (Exception e) {
            LOGGER.error("error，", e);
            return "error";
        }
    }

    /**
     * 批量发送消息
     *
     * @param msgs 消息集合
     */
    public void sendBatch(List<String> msgs) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            // 方法开启当前信道上的confirm机制。
            channel.confirmSelect();

            // 循环调用channel.basicPublish方法发送多条消息。
            msgs.forEach(msg -> {
                try {
                    channel.basicPublish(RabbitConfig.DIRECT_EXCHANGE_CONFIRM, RabbitConfig.ROUTE_KEY1, null, msg.getBytes());
                } catch (IOException e) {
                    LOGGER.error("message send error，msg：{}", msg);
                }
            });

            // waitForConfirms方法会阻塞，直到自上次调用以来发布的所有消息都被broker反馈了ack或者nack。
            boolean ack = channel.waitForConfirms();
            if (ack) {
                LOGGER.info("message send success");
            } else {
                LOGGER.error("message send failed");
            }
        } catch (Exception e) {
            LOGGER.error("message send error", e);
        }
    }

    /**
     * 异步发送消息
     *
     * @param msg 消息
     */
    public void sendSync(String msg) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.confirmSelect();

            // channel.addConfirmListener方法在channel上添加Confirm监听事件，
            channel.addConfirmListener(new ConfirmListener() {
                // handleNack方法处理消息发送失败的逻辑，
                @Override
                public void handleNack(long deliveryTag, boolean multiple) {
                    LOGGER.error("【sendSync】message send failed");
                    LOGGER.info("deliveryTag = {}, multiple = {}", deliveryTag, multiple);
                }

                // handleAck方法处理消息发送成功的逻辑。
                @Override
                public void handleAck(long deliveryTag, boolean multiple) {
                    LOGGER.info("【sendSync】message send success");
                    LOGGER.info("deliveryTag = {}, multiple = {}", deliveryTag, multiple);
                }
            });

            channel.basicPublish(RabbitConfig.DIRECT_EXCHANGE_CONFIRM, RabbitConfig.ROUTE_KEY1, null, msg.getBytes());

        } catch (Exception e) {
            LOGGER.error("【sendSync】message send error", e);
        }
    }

    /**
     * confirm机制
     *
     * @param msg 单条消息
     * @return 发送结果信息
     */
    public void confirm(String msg) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();
        ) {
            //创建Exchange
            channel.exchangeDeclare("confirm.exchange", BuiltinExchangeType.DIRECT, true, false, new HashMap<>());
            //创建Queue
            channel.queueDeclare("confirm.queue", true, false, false, new HashMap<>());
            //绑定路由
            channel.queueBind("confirm.queue", "confirm.exchange", "confirm");
            channel.confirmSelect();
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    LOGGER.info("ack : deliveryTag = {},multiple = {}", deliveryTag, multiple);
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    LOGGER.error("nack : deliveryTag = {},multiple = {}", deliveryTag, multiple);
                }
            });
            String msgTemplate = "测试消息[%d]";
            for (int i = 0; i < 10; i++) {
                channel.basicPublish("confirm.exchange", "confirm", new AMQP.BasicProperties(), String.format(msgTemplate, i).getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            LOGGER.error("error，", e);
        }
    }

    /**
     * Exchange路由到队列失败
     * <p>
     * 在生产者将消息推送到RabbitMQ时，我们可以通过事务或者confirm模式来保证消息不会丢失。
     * 但是这两种措施只能保证消息到达Exchange，如果我们的消息无法根据RoutingKey到达对应的Queue中，那么我们的消息最后就会丢失。
     * 对于这种情况，RabbitMQ中在发送消息时提供了mandatory参数。如果mandatory为true时，Exchange根据自身的类型
     * 和RoutingKey无法找到对应的Queue，它将不会丢掉该消息，而是会将消息返回给生产者。
     *
     * @param msg 单条消息
     * @return 发送结果信息
     */
    public void exchange(String msg) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();
        ) {
            // 创建Exchange
            channel.exchangeDeclare("mandatory.exchange", BuiltinExchangeType.DIRECT, true, false, new HashMap<>());
            // 创建Queue
            channel.queueDeclare("mandatory.queue", true, false, false, new HashMap<>());
            // 绑定路由
            channel.queueBind("mandatory.queue", "mandatory.exchange", "mandatory");

            channel.addReturnListener(new ReturnListener() {
                @Override
                public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    LOGGER.error("replyCode = {},replyText ={},exchange={},routingKey={},body={}", replyCode, replyText, exchange, routingKey, new String(body));
                }
            });
            // 设置mandatory = true
            // void basicPublish(String exchange, String routingKey, boolean mandatory, BasicProperties props, byte[] body)
            channel.basicPublish("mandatory.exchange", "mandatory-1", true, new AMQP.BasicProperties(), "测试mandatory的消息".getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            LOGGER.error("error，", e);
        }
    }

}


