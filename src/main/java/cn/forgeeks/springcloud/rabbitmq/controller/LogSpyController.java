package cn.forgeeks.springcloud.rabbitmq.controller;

import cn.forgeeks.springcloud.rabbitmq.common.RabbitLogSender;
import cn.forgeeks.springcloud.rabbitmq.dto.MessageDto;
import cn.forgeeks.springcloud.rabbitmq.dto.MessageSendDto;
import cn.forgeeks.springcloud.rabbitmq.dto.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for log spy.
 */
@RestController
@RequestMapping(value = "/log")
public class LogSpyController {

    @Autowired
    RabbitLogSender rabbitSender;

    @PostMapping(value = "/sendMsg")
    public ResultDto sendMsg(@RequestBody MessageDto message){
        rabbitSender.send(message);
        return new ResultDto(200,"Success.");
    }

    @PostMapping(value = "/sendMsgWithCustomParams")
    public ResultDto sendMsgWithCustomParams(@RequestBody MessageSendDto message){
        rabbitSender.sendWithCustomParams(message.getMessage(),message.getExchange() , message.getBindingKey());
        return new ResultDto(200,"Success.");
    }

}
