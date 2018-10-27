package cn.forgeeks.springcloud.rabbitmq.common;

import cn.forgeeks.springcloud.rabbitmq.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Rabbit sender with callback.
 * @author hechao
 */
@Component
public class RabbitLogSender {

    private static final Logger log = LoggerFactory.getLogger(RabbitLogSender.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * Send a message with topic exchange and ordinary info binding key.
     */
    public void send(MessageDto message){
        this.rabbitTemplate.convertAndSend(Consts.EXCHANGE_MYTH_TOPIC,Consts.ROUTING_MYTH_LOG_ERROR,message);
        log.info("Message is pushed : [{}].",message);
    }

    /**
     * Send a message with custom exchange and binding key.
     */
    public void sendWithCustomParams(MessageDto message,String exchange , String bindingKey){
        this.rabbitTemplate.convertAndSend(exchange,bindingKey,message);
        log.info("Message is pushed : [{}].",message);
    }

}
