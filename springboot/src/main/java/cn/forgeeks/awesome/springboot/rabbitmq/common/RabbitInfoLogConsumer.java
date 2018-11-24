package cn.forgeeks.awesome.springboot.rabbitmq.common;

import cn.forgeeks.awesome.springboot.rabbitmq.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *   INFO日志消费者
 */
@Component
@RabbitListener(queues = Consts.QUEUE_MYTH_LOG_INFO)
public class RabbitInfoLogConsumer {

    private static final Logger log = LoggerFactory.getLogger(RabbitInfoLogConsumer.class);

    @RabbitHandler
    public void process(MessageDto message){
        log.info("普通日志消费者:RabbitAllLogConsumer get a message : [{}]",message);
    }

}
