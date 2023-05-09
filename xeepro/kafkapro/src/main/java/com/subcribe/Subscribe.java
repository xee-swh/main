package com.subcribe;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Title Subscribe.java
 * @Description 订阅多个主题的全部分区
 */
@Component
public class Subscribe implements CommandLineRunner {

    @Autowired
    private KafkaConsumer<String, String> consumer;

    @Override
    public void run(String... args) throws Exception {
        try {
            while (true) {
                /**
                 * poll() 方法返回一个记录列表。
                 * 每条记录都包含了记录所属主题的信息、记录所在分区的信息、记录在分区里的偏移量，以及记录的键值对。
                 * 我们一般会遍历这个列表，逐条处理这些记录。
                 * 传给poll() 方法的参数是一个超时时间，用于控制 poll() 方法的阻塞时间（在消费者的缓冲区里没有可用数据时会发生阻塞）。
                 * 如果该参数被设为 0，poll() 会立即返回，否则它会在指定的毫秒数内一直等待 broker 返回数据。
                 * 而在经过了指定的时间后，即使还是没有获取到数据，poll()也会返回结果。
                 *
                 * 这里如果刚开始consumer没订阅任何主题，会报错
                 */
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("topic = " + record.topic() +
                            ", partition = " + record.partition() +
                            ", offset = " + record.offset() +
                            ", message = " + record.value());
                }

                /**
                 * 手动提交offset的方法有两种：
                 * 分别是commitSync（同步提交）和commitAsync（异步提交）。
                 * 两者的相同点是，都会将本次poll的一批数据最高的偏移量提交；
                 * 不同点是，commitSync会失败重试，一直到提交成功（如果由于不可恢复原因导致，也会提交失败）；
                 * 而commitAsync则没有失败重试机制，故有可能提交失败。
                 */
                consumer.commitSync();
            }
        } finally {
            /**
             * 在退出应用程序之前使用 close() 方法关闭消费者。
             * 网络连接和 socket 也会随之关闭，并立即触发一次再均衡，而不是等待群组协调器发现它不再发送心跳并认定它已死亡，
             * 因为那样需要更长的时间，导致整个群组在一段时间内无法读取消息。
             */
            consumer.close();
        }
    }
}