package cn.forgeeks.awesome.redis.common;

import cn.forgeeks.service.common.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumer for all logs.
 * @author hechao
 */
@Component
@RabbitListener(queues = Consts.QUEUE_MYTH_LOG_ALL)
public class RabbitAllLogConsumer {

    private static final Logger log = LoggerFactory.getLogger(RabbitAllLogConsumer.class);

    /**
     * Put real work job here where catch the msg to be comsumed.
     */
    @RabbitHandler
    public void process(MessageDto message){
        log.info("全局日志消费者:RabbitAllLogConsumer get a message : [{}]",message);
    }

}
