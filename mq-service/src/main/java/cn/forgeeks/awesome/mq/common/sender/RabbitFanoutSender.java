package cn.forgeeks.awesome.mq.common.sender;

import cn.forgeeks.awesome.common.dto.MessageDto;
import cn.forgeeks.awesome.mq.common.config.MqConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitFanoutSender {

    public static Logger log = LoggerFactory.getLogger(RabbitFanoutSender.class);

    @Autowired
    public RabbitTemplate rabbitTemplate;

    public void send(MessageDto messageDto){
        // 注意广播模式下要把路由设置成空字符串
        rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_FANOUT  ,"", messageDto );
        log.info("广播模式生产者生产一条消息:[{}]" , messageDto);
    }

}
