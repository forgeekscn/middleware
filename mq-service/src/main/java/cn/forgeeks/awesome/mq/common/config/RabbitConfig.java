package cn.forgeeks.awesome.mq.common.config;

import cn.forgeeks.awesome.mq.common.consumer.AbstarctCommonConsumer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * 队列初始化
 */

@Configuration
public class RabbitConfig {

    @Value("${spring.rabbit.host}")
    public String addresses;

    @Value("${spring.rabbit.port}")
    public String port;

    @Value("${spring.rabbit.username}")
    public String username;

    @Value("${spring.rabbit.password}")
    public String password;

    @Value("${spring.rabbit.virtual-host}")
    public String virtualHost;

    @Value("${spring.rabbit.publisher-confirms}")
    public boolean publisherConfirms;

    @Value("${spring.rabbit.publisher-returns}")
    public boolean publisherReturns;


    @Value("${spring.rabbit.listener.concurrency}")
    public Integer concurrency;


    @Value("${spring.rabbit.listener.max-concurrency}")
    public Integer maxConcurrency;


    @Value("${spring.rabbit.listener.prefetch}")
    public Integer prefetch;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setAddresses(addresses + ":" + port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);

        // 设置为发送方确认模式
        cachingConnectionFactory.setPublisherConfirms(publisherConfirms);
        cachingConnectionFactory.setPublisherReturns(publisherReturns);

        return cachingConnectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate getRabbitTemplate() {
        RabbitTemplate rabbitTemplate= new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMandatory(true);
        return  rabbitTemplate;
    }

    /**
     * 构建交换机
     */
    @Bean
    public TopicExchange exchangeMythTopic() {
        return new TopicExchange(MqConsts.EXCHANGE_MYTH_TOPIC);
    }

    /**
     * 广播模式交换器
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(MqConsts.EXCHANGE_FANOUT);
    }
    /**
     * 构建直连Direct交换机
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(MqConsts.EXCHANGE_DIRECT);
    }

    /**
     * 构建error日志队列
     */
    @Bean
    public Queue queueMythLogError() {
        return new Queue(MqConsts.QUEUE_MYTH_LOG_ERROR);
    }

    /**
     * 构建INFO日志队列
     */
    @Bean
    public Queue queueMythLogInfo() {
        return new Queue(MqConsts.QUEUE_MYTH_LOG_INFO);
    }

    /**
     * 构建所有日志队列
     */
    @Bean
    public Queue queueMythLogAll() {
        return new Queue(MqConsts.QUEUE_MYTH_LOG_ALL);
    }

    /**
     * 构建邮件队列
     */
    @Bean
    public Queue queueMailAll() {
        return new Queue(MqConsts.QUEUE_MYTH_MAIL_ALL);
    }

    /**
     * 构建秒杀订单队列
     */
    @Bean()
    public Queue queueOrderAll() {
        return new Queue(MqConsts.QUEUE_MYTH_ORDER_ALL, true);
    }

    /**
     * 构建direct模式专用的队列
     */
    @Bean
    public Queue queueDirect() {
        return new Queue(MqConsts.QUEUE_DIRECT_MSG);
    }

    /**
     * 构建Fanout模式的队列A
     */
    @Bean
    public Queue queueFanoutA() {
        return QueueBuilder.durable(MqConsts.QUEUE_FANOUT_A).build();
    }

    /**
     * 构建Fanout模式的队列B
     */
    @Bean
    public Queue queueFanoutB() {
        return QueueBuilder.durable(MqConsts.QUEUE_FANOUT_B).build();
    }


    @Bean
    public Queue queueNoConsumer() {
        return new Queue(MqConsts.QUEUE_NOCONSUMER , true , false, false);
        // return QueueBuilder.durable(MqConsts.QUEUE_NOCONSUMER).build();
    }

    @Bean
    public Binding bindingQueueNoconsumer( ) {
        return BindingBuilder.bind(queueNoConsumer()).to(directExchange()).with("queue.noconsumer");
    }

    @Bean
    public SimpleMessageListenerContainer buildQueueNoConsumer( @Qualifier("RabbitTestNoConsumer") AbstarctCommonConsumer abstarctCommonConsumer  ) {
        return getContainer(abstarctCommonConsumer,queueNoConsumer());
    }



    /**
     * 构建直连DIRECt绑定
     */
    @Bean
    public Binding bindingQueueDirect(Queue queueDirect, DirectExchange directExchange) {
        return BindingBuilder.bind(queueDirect).to(directExchange).with(MqConsts.ROUTING_WITH_DIRECT_QUEUE_AND_EXCHANGE);
    }

    /**
     * 构建广播模式绑定A
     */
    @Bean
    public Binding bindingQueueFanoutA(Queue queueFanoutA, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueFanoutA).to(fanoutExchange);
    }

    /**
     * 构建广播模式绑定B
     */
    @Bean
    public Binding bindingQueueFanoutB(Queue queueFanoutB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueFanoutB).to(fanoutExchange);
    }

    /**
     * 构建所有日志绑定路由
     */
    @Bean
    public Binding bindingQueueMythLogAll(Queue queueMythLogAll, TopicExchange exchangeMythTopic) {
        return BindingBuilder.bind(queueMythLogAll).to(exchangeMythTopic).with(MqConsts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ALL);
    }

    /**
     * 构建错误日志绑定路由
     */
    @Bean
    public Binding bindingQueueMythLogError(Queue queueMythLogError, TopicExchange exchangeMythTopic) {
        return BindingBuilder.bind(queueMythLogError).to(exchangeMythTopic).with(MqConsts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ERROR);
    }

    /**
     * 构建普通日志绑定路由
     */
    @Bean
    public Binding bindingQueueMythLogInfo(Queue queueMythLogInfo, TopicExchange exchangeMythTopic) {
        return BindingBuilder.bind(queueMythLogInfo).to(exchangeMythTopic).with(MqConsts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_INFO);
    }

    /**
     * 构建邮件绑定路由
     */
    @Bean
    public Binding bindingQueueMythMail(Queue queueMailAll, TopicExchange exchangeMythTopic) {
        return BindingBuilder.bind(queueMailAll).to(exchangeMythTopic).with(MqConsts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_MAIL);
    }


    /**
     * 构建秒杀订单绑定路由
     */
    @Bean
    public Binding bindingQueueMythOrder(Queue queueOrderAll, TopicExchange exchangeMythTopic) {
        return BindingBuilder.bind(queueOrderAll).to(exchangeMythTopic).with(MqConsts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_ORDER_ALL);
    }

    /**
     * 构建秒杀订单特有的监听器
     */
    @Bean
    public SimpleMessageListenerContainer buildDeadQueueConsumer( @Qualifier("RabbitDealetterProcessConsumer") AbstarctCommonConsumer abstarctCommonConsumer  ) {
        return getContainer(abstarctCommonConsumer,processDeadLetterQueue());
    }






    @Bean
    public Queue queueCommonFanout() {
        return QueueBuilder.durable(MqConsts.QUEUE_FANOUT_COMMON).build();
    }


    @Bean
    public SimpleMessageListenerContainer buildNackQueueConsumer( @Qualifier("RabbitNackMessageProcessConsumer") AbstarctCommonConsumer abstarctCommonConsumer  ) {
        return getContainer(abstarctCommonConsumer, queueCommonFanout());
    }

    @Bean
    public Binding queueCommonFanoutBinding() {
        return BindingBuilder.bind(queueCommonFanout()).to(fanoutExchangeCommon());
    }

    @Bean
    public FanoutExchange fanoutExchangeCommon() {
        return new FanoutExchange(MqConsts.EXCHANGE_FANOUT_COMMON,true,false);
    }





    public SimpleMessageListenerContainer getContainer(AbstarctCommonConsumer abstarctCommonConsumer, Queue queue ) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setMessageConverter(new Jackson2JsonMessageConverter());
        // 并发配置
        container.setConcurrentConsumers(concurrency);
        container.setMaxConcurrentConsumers(maxConcurrency);
        container.setPrefetchCount(prefetch);
        container.setQueues(queue);
        container.setMessageListener(abstarctCommonConsumer);
        //  手动确认ack机制 防止消息丢失
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }




    /**
     * 构建死信队列
     */
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(MqConsts.EXCHANGE_DEADLETTER, true, false);
    }

    @Bean
    public Queue processDeadLetterQueue() {
        return new Queue(MqConsts.QUEUE_DEADLETTER_PROCESS, true, false, false);
    }

    @Bean
    public Queue receiverDeadLetterQueue() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", MqConsts.EXCHANGE_DEADLETTER);
        map.put("x-dead-letter-routing-key", MqConsts.QUEUE_DEADLETTER_PROCESS);
        Queue queue = new Queue(MqConsts.QUEUE_DEADLETTER_RECEIVER, true, false, false, map);
        return queue;
    }

    @Bean
    public Binding queueProcessBinding() {
        return BindingBuilder.bind(processDeadLetterQueue()).to(deadLetterExchange()).with(MqConsts.QUEUE_DEADLETTER_PROCESS);
    }


    @Bean
    public Binding queueReceiverBinding() {
        return BindingBuilder.bind(receiverDeadLetterQueue()).to(deadLetterExchange()).with(MqConsts.QUEUE_DEADLETTER_RECEIVER);
    }


}
