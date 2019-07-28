package cn.forgeeks.service.common.service;

import cn.forgeeks.awesome.redis.common.RabbitLogSender;
import cn.forgeeks.service.common.dto.MessageDto;
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
