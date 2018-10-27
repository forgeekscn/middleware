package cn.forgeeks.springcloud.rabbitmq.common;

import cn.forgeeks.springcloud.rabbitmq.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumer for error logs.
 * @author hechao
 */
@Component
@RabbitListener(queues = Consts.QUEUE_MYTH_LOG_ERROR)
public class RabbitErrorLogConsumer {

    private static final Logger log = LoggerFactory.getLogger(RabbitErrorLogConsumer.class);

    /**
     * Put real work job here where catch the msg to be comsumed.
     */
    @RabbitHandler
    public void process(MessageDto message){
        log.info("RabbitErrorLogConsumer get a message : [{}].",message);
        try {
            Thread.sleep(Consts.TIME_OF_WORKJOB);
        } catch (InterruptedException e) {
            log.error("Exp during sleep [{}].",e);
        }
        log.info("Work finished.");
    }

}
