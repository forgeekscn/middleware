package cn.forgeeks.awesome.mq.common;

import cn.forgeeks.awesome.common.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitDirectSender {

    public static Logger log = LoggerFactory.getLogger(RabbitDirectSender.class);

    @Autowired
    public RabbitTemplate rabbitTemplate;

    public void send(MessageDto messageDto){
        rabbitTemplate.convertAndSend(Consts.EXCHANGE_DIRECT , Consts.ROUTING_WITH_DIRECT_QUEUE_AND_EXCHANGE ,messageDto);
        log.info("Direct直连生产者生产一条消息[{}]",messageDto);
    }

}
