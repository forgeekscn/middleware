package cn.forgeeks.awesome.mq.common.consumer;

import cn.forgeeks.awesome.common.date.DateUtils;
import cn.forgeeks.awesome.common.dto.MessageDto;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component(value = "RabbitNackMessageProcessConsumer")
public class RabbitNackMessageProcessConsumer extends AbstarctCommonConsumer {

    @Override
    public void processMessage(Message message, Channel channel) {
        MessageDto rs = byteArrayToObject( message.getBody());
        log.info("111处理普通消息{} , 消息详情 {} ", DateUtils.getFormatDateStr() , JSONObject.toJSONString(rs));
    }



    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        Long tag = message.getMessageProperties().getDeliveryTag();
        byte[] body = message.getBody();
        MessageDto msg = byteArrayToObject(body);
        try {
            processMessage(message , channel);

            channel.basicAck(tag, true);
        } catch (Exception e) {
            channel.basicReject(tag, false);
            log.error("失败:[{}]", e);
        }

    }

}