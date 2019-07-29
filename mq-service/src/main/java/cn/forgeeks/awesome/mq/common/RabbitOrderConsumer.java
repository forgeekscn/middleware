package cn.forgeeks.awesome.mq.common;

import cn.forgeeks.awesome.common.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component
//@RabbitListener( queues = Consts.QUEUE_MYTH_ORDER_ALL)
public class RabbitOrderConsumer {
    Logger log = LoggerFactory.getLogger(RabbitOrderConsumer.class);

   //  @RabbitHandler
    public void process(MessageDto messageDto){
        log.info("秒杀订单消费者:[{}]",messageDto);
    }
}

