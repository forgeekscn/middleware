package cn.forgeeks.awesome.springboot.rabbitmq.common;

import cn.forgeeks.awesome.springboot.rabbitmq.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
//@RabbitListener( queues = Consts.QUEUE_MYTH_ORDER_ALL)
public class RabbitOrderConsumer {
    Logger log = LoggerFactory.getLogger(RabbitOrderConsumer.class);

   //  @RabbitHandler
    public void process(MessageDto messageDto){
        log.info("秒杀订单消费者:[{}]",messageDto);
    }
}

