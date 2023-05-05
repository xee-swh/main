package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * kakfa启动类
 */
@SpringBootApplication
public class KafkaAppliction {

    public static void main(String[] args) {
        SpringApplication.run(KafkaAppliction.class);
    }

}
