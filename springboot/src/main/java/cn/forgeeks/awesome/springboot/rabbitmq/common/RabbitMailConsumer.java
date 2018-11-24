package cn.forgeeks.awesome.springboot.rabbitmq.common;

import cn.forgeeks.awesome.springboot.rabbitmq.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 邮件消费者
 */
@Component
@RabbitListener(queues = Consts.QUEUE_MYTH_MAIL_ALL)
public class RabbitMailConsumer {
    private static final Logger log = LoggerFactory.getLogger(RabbitMailConsumer.class);

    @RabbitHandler
    public void process(MessageDto mail){
        log.info("邮件消费者[{}]",mail);
    }

}

