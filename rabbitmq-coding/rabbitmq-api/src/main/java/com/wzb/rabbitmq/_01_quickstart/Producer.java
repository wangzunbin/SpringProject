package com.wzb.rabbitmq._01_quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:Procuder  <br/>
 * Funtion: 生产者 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/8 16:35   <br/>
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1. 创建一个connetionfactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.101");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        // 2. 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3. 通过connection创建一个channel
        Channel channel = connection.createChannel();

        // 4. 通过channel发送数据
        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ!!!!";
            // 参数1:  exchange(没有设置的话, 就会往这个AMQP default发), 参数2:  routingKey
            channel.basicPublish("", "test001", null, msg.getBytes());
        }

        // 5. 记得关闭连接
        channel.close();
        connection.close();
    }
}
