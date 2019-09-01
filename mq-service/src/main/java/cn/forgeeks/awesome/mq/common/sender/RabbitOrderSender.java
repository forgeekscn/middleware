package cn.forgeeks.awesome.mq.common.sender;

import cn.forgeeks.awesome.common.dto.MessageDto;
import cn.forgeeks.awesome.mq.common.config.MqConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderSender {

    Logger log = LoggerFactory.getLogger(RabbitOrderSender.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 发送订单消息
     */
    public void sendOrderMsg(MessageDto messageDto){
        rabbitTemplate.convertAndSend(  MqConsts.EXCHANGE_MYTH_TOPIC , MqConsts.QUEUE_MYTH_ORDER_ALL , messageDto);
        log.info("秒杀订单生产者:[{}]",messageDto);
    }

}
