package com.wzb.rabbitmq.api._06_consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * ClassName:MyConsumer  <br/>
 * Funtion: 自定义消费端  DefaultConsumer里面有很多方法 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/8 20:35   <br/>
 */
public class MyConsumer extends DefaultConsumer {


    public MyConsumer(Channel channel) {
        super(channel);
    }


    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.err.println("-----------consume message----------");
        System.err.println("consumerTag: " + consumerTag);
        System.err.println("envelope: " + envelope);
        System.err.println("properties: " + properties);
        System.err.println("body: " + new String(body));
    }
}
