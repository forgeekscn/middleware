package cn.forgeeks.awesome.mq.common.consumer;

import cn.forgeeks.awesome.common.date.DateUtils;
import cn.forgeeks.awesome.common.dto.MessageDto;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Slf4j
// @ConditionalOnProperty( value = "${queue.noconsumer.status}" , havingValue = "no")
@Component(value = "RabbitTestNoConsumer")
public class RabbitTestNoConsumer extends AbstarctCommonConsumer {
    @Override
    public void processMessage(Message message, Channel channel) {
        MessageDto rs = byteArrayToObject( message.getBody());
        log.info("RabbitTestNoConsumer  消息 {},  详情 {} ", DateUtils.getFormatDateStr() , JSONObject.toJSONString(rs));
    }

}