package cn.forgeeks.awesome.mq.common.sender;

import cn.forgeeks.awesome.common.dto.MessageDto;
import cn.forgeeks.awesome.mq.common.config.MqConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitDeadLetterSender extends AbstractRabbitCommonSender {

    @Override
    public void send(MessageDto messageDto) {
        sendByConfirmMode(MqConsts.EXCHANGE_DEADLETTER, MqConsts.QUEUE_DEADLETTER_RECEIVER, messageDto,10);
        log.info("延时消息进入队列 {} " , messageDto);
    }
}