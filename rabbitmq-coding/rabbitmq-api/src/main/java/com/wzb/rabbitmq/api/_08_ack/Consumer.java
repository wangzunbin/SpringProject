package com.wzb.rabbitmq.api._08_ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * ClassName:Consumer  <br/>
 * Funtion: ack应答机制 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/8 20:34   <br/>
 */
public class Consumer {

    public static void main(String[] args) throws Exception {


        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.104");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.#";
        String queueName = "test_ack_queue";

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
        //1 限流方式  第一件事就是 autoAck设置为 false

        // 参数设置成fasle, 就是需要手动ack, 消费完之后才能ack
        channel.basicConsume(queueName, false, new MyConsumer(channel));
    }
}
