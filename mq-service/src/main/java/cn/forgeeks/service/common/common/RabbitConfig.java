package cn.forgeeks.service.common.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 *  队列初始化
 */

@Configuration
public class RabbitConfig {

    Logger log = LoggerFactory.getLogger(RabbitConfig.class);

    @Value("${spring.rabbit.host}")
    private String addresses;

    @Value("${spring.rabbit.port}")
    private String port;

    @Value("${spring.rabbit.username}")
    private String username;

    @Value("${spring.rabbit.password}")
    private String password;

    @Value("${spring.rabbit.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbit.publisher-confirms}")
    private boolean publisherConfirms;


    @Value("${spring.rabbit.listener.concurrency}")
    private Integer  concurrency;


    @Value("${spring.rabbit.listener.max-concurrency}")
    private Integer  maxConcurrency;


    @Value("${spring.rabbit.listener.prefetch}")
    private Integer  prefetch;

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setAddresses(addresses+":"+port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        cachingConnectionFactory.setPublisherConfirms(publisherConfirms);
        log.info("构建队列连接池[{}]",cachingConnectionFactory);
        return cachingConnectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate getRabbitTemplate(){
        return new RabbitTemplate(connectionFactory());
    }

    /**
     *  构建交换机
     */
    @Bean
    public TopicExchange exchangeMythTopic(){
        log.info("构建交换机[{}]", Consts.EXCHANGE_MYTH_TOPIC );
        return new TopicExchange(Consts.EXCHANGE_MYTH_TOPIC );
    }

    /**
     *  广播模式交换器
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        log.info("广播模式交换器[{}]", Consts.EXCHANGE_FANOUT);
        return  new FanoutExchange(Consts.EXCHANGE_FANOUT);
    }

    /**
     *  构建直连Direct交换机
     */
        @Bean
        public DirectExchange directExchange(){
            log.info("构建直连Direct交换机[{}]",Consts.EXCHANGE_DIRECT);
            return new DirectExchange(Consts.EXCHANGE_DIRECT);
        }

    /**
     * 构建error日志队列
     */
    @Bean
    public Queue queueMythLogError() {
        log.info("构建error日志队列[{}]",Consts.QUEUE_MYTH_LOG_ERROR);
        return new Queue(Consts.QUEUE_MYTH_LOG_ERROR);
    }

    /**
     * 构建INFO日志队列
     */
    @Bean
    public Queue queueMythLogInfo() {
        log.info("构建INFO日志队列[{}]",Consts.QUEUE_MYTH_LOG_INFO);
        return new Queue(Consts.QUEUE_MYTH_LOG_INFO);
    }

    /**
     *  构建所有日志队列
     */
    @Bean
    public Queue queueMythLogAll(){
        log.info("构建所有日志队列[{}]",Consts.QUEUE_MYTH_LOG_ALL);
        return  new Queue(Consts.QUEUE_MYTH_LOG_ALL);
    }

    /**
     *  构建邮件队列
     */
    @Bean
    public Queue queueMailAll(){
        log.info("构建邮件队列[{}]",Consts.QUEUE_MYTH_MAIL_ALL);
        return  new Queue(Consts.QUEUE_MYTH_MAIL_ALL);
    }

    /**
     *  构建秒杀订单队列
     */
    @Bean()
    public Queue queueOrderAll(){
        log.info("构建秒杀订单队列[{}]",Consts.QUEUE_MYTH_ORDER_ALL);
        return  new Queue(Consts.QUEUE_MYTH_ORDER_ALL ,true);
    }

    /**
     *  构建direct模式专用的队列
     */
    @Bean
    public Queue queueDirect(){
        log.info(" 构建direct模式专用的队列[{}]",Consts.QUEUE_DIRECT_MSG);
        return new Queue(Consts.QUEUE_DIRECT_MSG );
    }

    /**
     *  构建Fanout模式的队列A
     */
    @Bean
    public Queue queueFanoutA(){
        log.info("构建Fanout模式的队列A", Consts.QUEUE_FANOUT_A);
        return QueueBuilder.durable(Consts.QUEUE_FANOUT_A).build();
    }

    /**
     *  构建Fanout模式的队列B
     */
    @Bean
    public Queue queueFanoutB(){
        log.info("构建Fanout模式的队列A", Consts.QUEUE_FANOUT_B);
        return  QueueBuilder.durable(Consts.QUEUE_FANOUT_B).build();
    }

    /**
     *  构建直连DIRECt绑定
     */
    @Bean
    public Binding bindingQueueDirect(Queue queueDirect , DirectExchange  directExchange){
        log.info("构建直连DIRECt绑定[{}]",Consts.ROUTING_WITH_DIRECT_QUEUE_AND_EXCHANGE);
        return BindingBuilder.bind(queueDirect).to(directExchange).with(Consts.ROUTING_WITH_DIRECT_QUEUE_AND_EXCHANGE);
    }

    /**
     *  构建广播模式绑定A
     */
    @Bean
    public Binding bindingQueueFanoutA(Queue queueFanoutA  , FanoutExchange fanoutExchange ){
        log.info("构建广播模式绑定A");
        return BindingBuilder.bind(queueFanoutA).to(fanoutExchange);
    }

    /**
     *  构建广播模式绑定B
     */
    @Bean
    public Binding bindingQueueFanoutB( Queue queueFanoutB , FanoutExchange fanoutExchange ){
        log.info("构建广播模式绑定B");
        return BindingBuilder.bind(queueFanoutB).to(fanoutExchange);
    }

    /**
     *  构建所有日志绑定路由
     */
    @Bean
    public Binding bindingQueueMythLogAll(Queue queueMythLogAll , TopicExchange exchangeMythTopic ){
        log.info("构建所有日志绑定路由[{}]",Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ALL);
        return BindingBuilder.bind(queueMythLogAll).to(exchangeMythTopic).with(Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ALL);
    }

    /**
     * 构建错误日志绑定路由
     */
    @Bean
    public Binding bindingQueueMythLogError(Queue queueMythLogError , TopicExchange exchangeMythTopic ){
        log.info("构建错误日志绑定路由[{}]",Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ERROR);
        return BindingBuilder.bind(queueMythLogError).to(exchangeMythTopic).with(Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ERROR);
    }

    /**
     * 构建普通日志绑定路由
     */
    @Bean
    public Binding bindingQueueMythLogInfo(Queue queueMythLogInfo , TopicExchange exchangeMythTopic ){
        log.info("构建普通日志绑定路由[{}]",Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_INFO);
        return BindingBuilder.bind(queueMythLogInfo).to(exchangeMythTopic).with(Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_INFO);
    }

    /**
     * 构建邮件绑定路由
     */
    @Bean
    public Binding bindingQueueMythMail(Queue queueMailAll , TopicExchange exchangeMythTopic ){
        log.info("构建邮件绑定路由[{}]",Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_MAIL);
        return BindingBuilder.bind(queueMailAll).to(exchangeMythTopic).with(Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_MAIL);
    }


    /**
     * 构建秒杀订单绑定路由
     */
    @Bean
    public Binding bindingQueueMythOrder(Queue queueOrderAll , TopicExchange exchangeMythTopic ){
        log.info("构建秒杀订单绑定路由[{}]",Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_ORDER_ALL);
        return BindingBuilder.bind(queueOrderAll).to(exchangeMythTopic).with(Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_ORDER_ALL);
    }

    /**
     *  构建秒杀订单特有的监听器
     */
    @Bean
    public SimpleMessageListenerContainer orderListenerContainer(@Qualifier("rabbitOrderListener") RabbitOrderListener rabbitOrderListener ,
                                                                 Queue  queueOrderAll,  ConnectionFactory connectionFactory ){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageConverter(new Jackson2JsonMessageConverter());

        // 并发配置
        container.setConcurrentConsumers(concurrency);
        container.setMaxConcurrentConsumers(maxConcurrency);
        container.setPrefetchCount(prefetch);

        //  消息确认机制
        container.setQueues(queueOrderAll);
        container.setMessageListener(rabbitOrderListener);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        log.info("构建秒杀订单特有的监听器[{}]",rabbitOrderListener);
        log.info("#########################   rabbit消息队列配置完成   ######################");
        return container;
    }

}
