package com.wzb.spring;

import com.wzb.spring.adapter.MessageDelegate;
import com.wzb.spring.convert.ImageMessageConverter;
import com.wzb.spring.convert.PDFMessageConverter;
import com.wzb.spring.convert.TextMessageConverter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * ClassName:RabbitMQConfig  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/8 22:10   <br/>
 */
@Configuration
@ComponentScan({"com.wzb.spring.*"})
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("192.168.0.104:5672");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }


    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 一定要加上这个, 否则spring容器不会加载rabbitAdmin类
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /************  消费者的一些设置 start  ************/
    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    /************  简单队列的绑定 start  ************/
    @Bean
    public TopicExchange exchange001() {
        return new TopicExchange("topic001", true, false);
    }

    @Bean
    public Queue queue001() {
        return new Queue("queue001", true); //队列持久
    }

    @Bean
    public Binding binding001() {
        return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
    }

    @Bean
    public TopicExchange exchange002() {
        return new TopicExchange("topic002", true, false);
    }

    @Bean
    public Queue queue002() {
        return new Queue("queue002", true); //队列持久
    }

    @Bean
    public Binding binding002() {
        return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
    }

    @Bean
    public Queue queue003() {
        return new Queue("queue003", true); //队列持久
    }

    @Bean
    public Binding binding003() {
        return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");
    }

    /************  简单队列的绑定 end  ************/


    @Bean
    public Queue queue_image() {
        return new Queue("image_queue", true); //队列持久
    }

    @Bean
    public Queue queue_pdf() {
        return new Queue("pdf_queue", true); //队列持久
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }


    /**
     * 简单消息监听容器
     * 此api是统一配置消费端的配置
     * 1. 这个类非常的强大, 我们可以对它进行很多设置, 对于消费者的配置项, 这个类都可以满足
     * 2. 监听队列(多个队列), 自动启动,自动声明功能
     * 3. 设置事务特性, 事务管理器,事务属性, 事务容量(并发), 是否开启事务, 回滚消息等
     * 4. 设置消息确认和自动确认模式, 是否重回队列, 异常捕获handle函数
     * 5. 设置消费者标签生成策略, 是否独占模式, 消费者属性等
     * 6. 设置具体的监听器, 消息转换器等等
     * 注意: SimpleMessageListenerContainer可以进行动态设置, 比如在运行中的应用可以动态的修改其消费者数量的大小,接收消息的模式等
     * 很多基于rabbitmq的自制定化后端管控台在进行动态设置的时候, 也是根据这一特性去实现的,所以可以看到springAMQP非常的强大
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 设置多个节点
        container.setQueues(queue001(), queue002(), queue003(), queue_image(), queue_pdf());
        // 设置当前的消费量
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(5);
        // 是否重回队列
        container.setDefaultRequeueRejected(false);
        // 是否签收, 自动签收
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setExposeListenerChannel(true);
        // 签收策略
        container.setConsumerTagStrategy(queue -> queue + "_" + UUID.randomUUID().toString());

        /*   // 消费者的的是什么消息
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            String msg = new String(message.getBody());
            System.err.println("----------消费者: " + msg);
        });
*/

        /*
         * 1 适配器方式. 默认是有自己的方法名字的：handleMessage
         * 可以自己指定一个方法的名字: consumeMessage
         * 也可以添加一个转换器: 从字节数组转换为String
         MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
         adapter.setDefaultListenerMethod("consumeMessage");
         adapter.setMessageConverter(new TextMessageConverter());
         container.setMessageListener(adapter);

         2 适配器方式: 我们的队列名称 和 方法名称 也可以进行一一的匹配

        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
        adapter.setMessageConverter(new TextMessageConverter());
        Map<String, String> queueOrTagToMethodName = new HashMap<>();
        queueOrTagToMethodName.put("queue001", "method1");
        queueOrTagToMethodName.put("queue002", "method2");
        adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
        container.setMessageListener(adapter);

         总结如下:
         ----------------------
         MessageListenerAdapter:
         setDefaultListenerMethod默认监听方法名称: 用于设置监听方法名称
         Delegate: 委托对象: 实际真实的委托对象, 用于处理消息
         queueOrTagToMethodName: 队列标识与方法名称组成的集合,  可以--进行队列与方法名称的匹配, 队列和方法名称绑定,
         即指定队列里的消息会被绑定的方法所接受处理
         -------------------------------------------------
         MessageConverter:
         需要:  我们在进行发送消息的时候, 正常情况下消息体为二进制的数据方式进行传输, 如果希望内部帮我们进行转换,
         或者指定自定义的转换器, 就需要用到MessageConverter
         用法:  自定义常用转换器: MessageConverter, 一般来讲都需要实现这个借口, 重写下面两个方法:
         toMessage java对象转换成message, formMessage: message对象转换为java对象
         ----------------------------------------------------
         常用的转换器还有:
         json转换器: Jackson2JsonMessageConverter: 进行java对象的转换功能
         DefaultJackson2JavaTypeMapper映射器: 可以进行java对象的映射关系
         自定义二进制转换器: 比如图片类型、PDF、PPT、流媒体


         */

        // 1.1 支持json格式的转换器
        /*
        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
         adapter.setDefaultListenerMethod("consumeMessage");

         Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
         adapter.setMessageConverter(jackson2JsonMessageConverter);

         container.setMessageListener(adapter);
         */


        // 1.2 DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象转换

      /*  MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
        adapter.setDefaultListenerMethod("consumeMessage");

        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();

        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
        // 需要在这里注意
        javaTypeMapper.setTrustedPackages("*");
        jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);

        adapter.setMessageConverter(jackson2JsonMessageConverter);
        container.setMessageListener(adapter);*/


        //1.3 DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象多映射转换
     /*    MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
         adapter.setDefaultListenerMethod("consumeMessage");
         Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
         DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();

         Map<String, Class<?>> idClassMapping = new HashMap<>();
         idClassMapping.put("order", com.wzb.spring.entity.Order.class);
         idClassMapping.put("packaged", com.wzb.spring.entity.Packaged.class);

         javaTypeMapper.setIdClassMapping(idClassMapping);

         jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
         adapter.setMessageConverter(jackson2JsonMessageConverter);
         container.setMessageListener(adapter);*/

        //1.4 ext convert

        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
        adapter.setDefaultListenerMethod("consumeMessage");

        //全局的转换器:
       ContentTypeDelegatingMessageConverter convert = new ContentTypeDelegatingMessageConverter();

        TextMessageConverter textConvert = new TextMessageConverter();
        convert.addDelegate("text", textConvert);
        convert.addDelegate("html/text", textConvert);
        convert.addDelegate("xml/text", textConvert);
        convert.addDelegate("text/plain", textConvert);

        Jackson2JsonMessageConverter jsonConvert = new Jackson2JsonMessageConverter();
        convert.addDelegate("json", jsonConvert);
        convert.addDelegate("application/json", jsonConvert);

        ImageMessageConverter imageConverter = new ImageMessageConverter();
        convert.addDelegate("image/png", imageConverter);
        convert.addDelegate("image", imageConverter);

        PDFMessageConverter pdfConverter = new PDFMessageConverter();
        convert.addDelegate("application/pdf", pdfConverter);


        adapter.setMessageConverter(convert);
        container.setMessageListener(adapter);

        return container;
    }

}
