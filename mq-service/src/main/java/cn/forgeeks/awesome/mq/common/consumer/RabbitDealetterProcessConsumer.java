package cn.forgeeks.awesome.mq.common.consumer;

import cn.forgeeks.awesome.common.date.DateUtils;
import cn.forgeeks.awesome.common.dto.MessageDto;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component(value = "RabbitDealetterProcessConsumer")
// @RabbitListener(queues= MqConsts.QUEUE_DEADLETTER_PROCESS)
public class RabbitDealetterProcessConsumer extends AbstarctCommonConsumer {

//    @RabbitHandler
//    public void process(MessageDto messageDto) {
//        log.info("延迟消息到期 {}, 开始处理  {} ", DateUtils.getFormatDateStr() , JSONObject.toJSONString(messageDto));
//    }


    @Override
    public void processMessage(Message message, Channel channel) {
        MessageDto rs = byteArrayToObject( message.getBody());
        log.info("延迟消息到期 {}, 消息详情 {} ", DateUtils.getFormatDateStr() , JSONObject.toJSONString(rs));
    }

}