package cn.forgeeks.springcloud.rabbitmq;

import cn.forgeeks.springcloud.rabbitmq.common.Consts;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    /**
     * Queue for error log.
     */
    @Bean
    public Queue queueMythLogError() {
        return new Queue(Consts.QUEUE_MYTH_LOG_ERROR);
    }

    /**
     * Queue for error info warn or etc... logs.
     */
    @Bean
    public Queue queueMythLogAll(){
        return  new Queue(Consts.QUEUE_MYTH_LOG_ALL);
    }

    /**
     * Exchange for topic mode.
     */
    @Bean
    public TopicExchange  exchangeMythTopic(){
        return new TopicExchange(Consts.EXCHANGE_MYTH_TOPIC);
    }

    /**
     * Bind exchange and log-all-queue with regular binding key.
     */
    @Bean
    public Binding bindingQueueMythLogAll(Queue queueMythLogAll , TopicExchange exchangeMythTopic ){
        return BindingBuilder.bind(queueMythLogAll).to(exchangeMythTopic).with(Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ALL);
    }

    /**
     * Bind exchange and log-error-queue with regular binding key.
     */
    @Bean
    public Binding bindingQueueMythLogError(Queue queueMythLogError , TopicExchange exchangeMythTopic ){
        return BindingBuilder.bind(queueMythLogError).to(exchangeMythTopic).with(Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ERROR);
    }

}
