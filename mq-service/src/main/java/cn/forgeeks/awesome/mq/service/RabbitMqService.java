package cn.forgeeks.awesome.mq.service;

import cn.forgeeks.awesome.common.dto.MessageDto;
import cn.forgeeks.awesome.mq.common.RabbitLogSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqService {

    @Autowired
    RabbitLogSender rabbitLogSender;

    public MessageDto sendAlllog(){
        MessageDto param = new MessageDto();
        param.setId("123");
        param.setConsumer("name");
        param.setContent("123123");
        rabbitLogSender.send(param);
        return param;
    }

}
