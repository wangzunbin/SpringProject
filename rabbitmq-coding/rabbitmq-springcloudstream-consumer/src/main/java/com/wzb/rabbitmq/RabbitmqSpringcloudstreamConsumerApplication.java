package com.wzb.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqSpringcloudstreamConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqSpringcloudstreamConsumerApplication.class, args);
    }

    /**
     * 使用spring cloud-stream的总结:
     * 1. 使用spring cloud stream非常简单, 只需要使用好三个注解就ok, 在实现高性能消息的生产和消费的场景非常适合, 但是使用springcloud
     stream 框架有一个非常大的问题就是不能实现可靠性的投递,也就是无法保证消息的100%可靠性, 会存在少量消息丢失的问题
       2. 这个原因是因为springcloudstream框架为了和Kafka兼顾所以在实际工作中使用它的目的就是针对高性能的消息通信的, 这点就是在当前版本spring
     cloud stream的定位
     */
}
