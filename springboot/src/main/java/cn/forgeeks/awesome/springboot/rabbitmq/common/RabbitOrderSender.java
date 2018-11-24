package cn.forgeeks.awesome.springboot.rabbitmq.common;

import cn.forgeeks.awesome.springboot.rabbitmq.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderSender {

    Logger log = LoggerFactory.getLogger(RabbitOrderListener.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 发送订单消息
     */
    public void sendOrderMsg(MessageDto messageDto){
        rabbitTemplate.convertAndSend(  Consts.EXCHANGE_MYTH_TOPIC , Consts.QUEUE_MYTH_ORDER_ALL , messageDto);
        log.info("秒杀订单生产者:[{}]",messageDto);
    }

}
