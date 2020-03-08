package com.wzb.rabbitmq.api._07_limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * ClassName:Consumer  <br/>
 * Funtion: 消费端限流, 主要是解决mq服务器堆积了很多的消息, 一旦服务上线处理不过来而待机 <br/>
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


        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.#";
        String queueName = "test_qos_queue";

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
//1 限流方式  第一件事就是 autoAck设置为 false

        // 参数1设置为0, 手动签收, 参数2: 会告诉rabbitmq不要同时给一个消费者推送多于N个消息, 即一旦有N个消息还没有ack, 则该consumer将block掉,直到有消息ack
        // 参数3: true\false是否将上面设置应用于channel, 简单来说, 就是上面的限制是channel级别的还是consumer级别
        channel.basicQos(0, 1, false);

        // 参数设置成fasle, 就是需要手动ack, 消费完之后才能ack
        channel.basicConsume(queueName, false, new MyConsumer(channel));
    }
}
