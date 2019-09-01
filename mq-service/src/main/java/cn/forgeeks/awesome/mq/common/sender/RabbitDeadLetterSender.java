package cn.forgeeks.awesome.mq.common.sender;

import cn.forgeeks.awesome.common.dto.MessageDto;
import cn.forgeeks.awesome.mq.common.config.MqConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitDeadLetterSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(MessageDto messageDto, long timeSec ) {
        //rabbit默认为毫秒级
        long times = timeSec * 1000;
        amqpTemplate.convertAndSend(MqConsts.EXCHANGE_DEADLETTER, MqConsts.QUEUE_DEADLETTER_RECEIVER, messageDto, message -> {
            message.getMessageProperties().setExpiration(String.valueOf(times));
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });
        log.info("延时消息{} 生成 {} " , timeSec , messageDto);
    }
}