package com.wzb.springboot.consumer;

import com.rabbitmq.client.Channel;
import com.wzb.springboot.entity.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ClassName:RabbitReceiver  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/9 22:51   <br/>
 */
@Component
public class RabbitReceiver {

    /*  @RabbitListener:
     *    1. 消费端监听注解, 这个对于在实际工作中非常的好用
     *    2. @RabbitListener是一个组合注解, 里面可以注解配置
     *    @QueueBinding、@queue、@exchange直接通过这个组合注解一次性搞定消费端交换机,队列,绑定,路由并且配置监听功能等
     *     PS: 由于类配置写在代码里非常不友好, 所以强烈建议大家使用配置文件配置
     * */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "queue-1",
                            durable = "true"),
                    exchange = @Exchange(value = "exchange-1",
                            durable = "true",
                            type = "topic",
                            ignoreDeclarationExceptions = "true"),
                    key = "springboot.*"
            )
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception {
        System.err.println("--------------------------------------");
        System.err.println("消费端Payload: " + message.getPayload());
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        //手工ACK
        channel.basicAck(deliveryTag, false);
    }


    /**
     * spring.rabbitmq.listener.order.queue.name=queue-2
     * spring.rabbitmq.listener.order.queue.durable=true
     * spring.rabbitmq.listener.order.exchange.name=exchange-1
     * spring.rabbitmq.listener.order.exchange.durable=true
     * spring.rabbitmq.listener.order.exchange.type=topic
     * spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions=true
     * spring.rabbitmq.listener.order.key=springboot.*
     *
     * @param order  接收一个对象
     * @param channel  通道
     * @param headers 头部
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
                    durable = "${spring.rabbitmq.listener.order.queue.durable}"),
            exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
                    type = "${spring.rabbitmq.listener.order.exchange.type}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
            key = "${spring.rabbitmq.listener.order.key}"
    )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload Order order,
                               Channel channel,
                               @Headers Map<String, Object> headers) throws Exception {
        System.err.println("--------------------------------------");
        System.err.println("消费端order: " + order.getId());
        // 手动签收
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //手工ACK
        channel.basicAck(deliveryTag, false);
    }
}
