package com.wzb.rabbitmq.api._07_limit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * ClassName:MyConsumer  <br/>
 * Funtion: 消费端限流 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/8 20:35   <br/>
 */
public class MyConsumer extends DefaultConsumer {

    private Channel channel;

    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }


    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.err.println("-----------consume message----------");
        System.err.println("consumerTag: " + consumerTag);
        System.err.println("envelope: " + envelope);
        System.err.println("properties: " + properties);
        System.err.println("body: " + new String(body));

        // 告诉mq服务器我已经收到了, 已经签收了, 可以传下一个数据过来
        channel.basicAck(envelope.getDeliveryTag(), false);
        /**
         *  1. 如果上面哪一行代码屏蔽掉之后, 结果是: 消费端只打印一个消息, 因为消费端没有告诉mq服务器没有签收
         *  2. 如果放开上面的哪一行basicAck的代码, 结果: 全部消息都消费了
         */
    }
}
