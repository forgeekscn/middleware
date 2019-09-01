package cn.forgeeks.awesome.mq.common.consumer;

import cn.forgeeks.awesome.common.date.DateUtils;
import cn.forgeeks.awesome.common.dto.MessageDto;
import cn.forgeeks.awesome.mq.common.config.MqConsts;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class RabbitDealetterProcessConsumer {

    @RabbitListener(queues= MqConsts.QUEUE_DEADLETTER_PROCESS)
    @RabbitHandler
    public void process(MessageDto messageDto) {
        log.info("延迟消息到期 {}, 开始处理  {} ", DateUtils.getFormatDateStr() , JSONObject.toJSONString(messageDto));
    }

}