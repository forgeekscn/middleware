package cn.forgeeks.awesome.mq.common.sender;

import cn.forgeeks.awesome.common.dto.MessageDto;
import cn.forgeeks.awesome.mq.common.config.MqConsts;
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
        rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_DIRECT , MqConsts.ROUTING_WITH_DIRECT_QUEUE_AND_EXCHANGE ,messageDto);
        log.info("Direct直连生产者生产一条消息[{}]",messageDto);
    }

}
