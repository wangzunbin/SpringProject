package com.wzb.rabbitmq.api._03_message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:Procuder  <br/>
 * Funtion: 生产者 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/8 16:35   <br/>
 */
public class Producer {

    /**
     * 需求1: 发送附加信息, 发送添加头信息, 接收也要把头信息拿出来
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1. 创建一个connetionfactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.104");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        // 2. 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3. 通过connection创建一个channel
        Channel channel = connection.createChannel();

        Map<String, Object> headers = new HashMap<>();
        headers.put("my1", "111");
        headers.put("my2", "222");
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                // 当为1时, 此消息不是持久化消息, 如果为2的话就是持久化
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                // 发送的消息什么时候过期, 10s自动移除(这个是消息级别的过期时间)
                .expiration("10000")
                .headers(headers)
                .build();

        // 4. 通过channel发送数据
        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ!!!!";
            // 参数1:  exchange(没有设置的话, 就会往这个AMQP default发), 参数2:  routingKey
            // 参数3:  添加附加信息和设置
            channel.basicPublish("", "test001", properties, msg.getBytes());
        }

        // 5. 记得关闭连接
        channel.close();
        connection.close();
    }
}
