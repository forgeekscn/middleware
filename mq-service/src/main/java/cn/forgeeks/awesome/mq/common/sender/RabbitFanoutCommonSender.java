package cn.forgeeks.awesome.mq.common.sender;

import cn.forgeeks.awesome.common.dto.MessageDto;
import cn.forgeeks.awesome.mq.common.config.MqConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitFanoutCommonSender extends AbstractRabbitCommonSender {

    @Override
    public void send(MessageDto messageDto) {
        sendBySimpleMode(MqConsts.EXCHANGE_FANOUT_COMMON, MqConsts.QUEUE_FANOUT_COMMON, messageDto );
    }


}