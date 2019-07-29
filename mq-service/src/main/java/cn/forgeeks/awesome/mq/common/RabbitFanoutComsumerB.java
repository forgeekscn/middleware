package cn.forgeeks.awesome.mq.common;

import cn.forgeeks.awesome.common.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = Consts.QUEUE_FANOUT_B)
public class RabbitFanoutComsumerB {

    public static Logger log = LoggerFactory.getLogger(RabbitFanoutComsumerB.class);

    @RabbitHandler
    public void process(MessageDto messageDto){
        log.info("广播消费者B消费一条消息[{}]",messageDto);
    }

}
