package com.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Properties;

/**
 * @Title Subscribe.java
 */
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Bean
    public KafkaConsumer<String, String> consumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaServer);
        props.put("group.id", "test-consumer-group");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        String[] topics = new String[]{"topic1"};
        consumer.subscribe(Arrays.asList(topics));
        return consumer;
    }

}