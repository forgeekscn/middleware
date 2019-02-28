package cn.forgeeks.service.common.common;

import cn.forgeeks.service.common.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  带回调的生产者
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
        this.rabbitTemplate.convertAndSend(Consts.EXCHANGE_MYTH_TOPIC,Consts.ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ALL,message);
        log.info("全局日志生产者:Message is pushed : [{}].",message);
    }

    /**
     * Send a message with custom exchange and binding key.
     */
    public void sendWithCustomParams(MessageDto message,String exchange , String bindingKey){
        this.rabbitTemplate.convertAndSend(exchange,bindingKey,message);
        log.info("生产者Message is pushed : [{}].",message);
    }

}
