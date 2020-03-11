package com.wzb.rabbitmq._01_quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * ClassName:Consumer  <br/>
 * Funtion: 消费者 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/8 16:35   <br/>
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        // 1. 创建一个connetionfactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.101");
        connectionFactory.setPort(5672);
        // 由于添加了haproxy代理, 设置的超时间是15s, 所以我这边的配置是10s
        connectionFactory.setRequestedHeartbeat(10);
        connectionFactory.setVirtualHost("/");

        // 2. 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3. 通过connection创建一个channel
        Channel channel = connection.createChannel();

        String queueName = "test001";

        // 4. 声明(创建)一个实例
        /**
         * 参数2: 是否持久化, 参数3: 独用,参数4: 当Yes, 代表当最后一个监听被移除之后, 该队列自动被删除
         */
        channel.queueDeclare(queueName, true, false, false, null);

        // 5. 创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        // 6. 设置channel
        channel.basicConsume(queueName, true, queueingConsumer);

//        new Thread(() -> {
            // 7. 获取消息
            while (true) {
                QueueingConsumer.Delivery delivery = null;
                try {
                    delivery = queueingConsumer.nextDelivery();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String msg = new String(delivery.getBody());
                System.out.println("消费端: " + msg);

            }
//        });
    }
}
