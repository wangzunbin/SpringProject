package com.wzb.rabbitmq.api._05_returnlistener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * ClassName:Producer  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/8 20:18   <br/>
 */
public class Producer {

    /**
     *  添加addReturnListener, 主要是解决一些消息不可到达, 就是消息没有消费者
     *
     */
    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.104");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchange = "test_return_exchange";
//        String routingKey = "return.save";
        String routingKeyError = "abc.save";

        String msg = "Hello RabbitMQ Return Message";

        channel.addReturnListener((replyCode, replyText, exchange1, routingKey1, properties, body) -> {

            System.err.println("---------handle  return----------");
            System.err.println("replyCode: " + replyCode);
            System.err.println("replyText: " + replyText);
            System.err.println("exchange: " + exchange1);
            System.err.println("routingKey: " + routingKey1);
            System.err.println("properties: " + properties);
            System.err.println("body: " + new String(body));
        });
        // 参数3: 如果为true, 则监听器会接收到路由不可达的消息, 然后进行后续处理,
        // 如果设置为false的话, 那么broke端自动删除该消息
        channel.basicPublish(exchange, routingKeyError, true, null, msg.getBytes());

    }
}
