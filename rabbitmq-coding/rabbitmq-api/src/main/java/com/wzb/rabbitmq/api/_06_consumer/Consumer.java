package com.wzb.rabbitmq.api._06_consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * ClassName:Consumer  <br/>
 * Funtion: 自定义消费端 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/8 20:34   <br/>
 */
public class Consumer {

    public static void main(String[] args) throws Exception {


        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 70是keepalived的虚拟地址
        connectionFactory.setHost("192.168.0.70");
        connectionFactory.setPort(5672);
        // 由于添加了haproxy代理, 设置的超时间是15s, 所以我这边的配置是10s
        connectionFactory.setRequestedHeartbeat(10);
        connectionFactory.setUsername("user");
        connectionFactory.setPassword("user");
        connectionFactory.setVirtualHost("test");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String exchangeName = "test_consumer_exchange";
        String routingKey = "consumer.#";
        String queueName = "test_consumer_queue";

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        channel.basicConsume(queueName, true, new MyConsumer(channel));
    }
}
