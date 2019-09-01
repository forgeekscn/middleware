package cn.forgeeks.awesome.mq.common.sender;

import cn.forgeeks.awesome.common.dto.MessageDto;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @Description 消息发送确认
 * <p>
 * ConfirmCallback  只确认消息是否正确到达 Exchange 中
 * ReturnCallback   消息没有正确到达队列时触发回调，如果正确到达队列不执行
 * <p>
 * 1. 如果消息没有到exchange,则confirm回调,ack=false
 * 2. 如果消息到达exchange,则confirm回调,ack=true
 * 3. exchange到queue成功,则不回调return
 * 4. exchange到queue失败,则回调return
 * @Author jxb
 * @Date 2019-04-04 16:57:04
 */
// @Component
@Slf4j
public abstract class AbstractRabbitCommonSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
//        rabbitTemplate.setConfirmCallback(this);            //指定 ConfirmCallback
//        rabbitTemplate.setReturnCallback(this);             //指定 ReturnCallback

    }

    void sendBySimpleMode(String exchange , String key, String message) {
        this.rabbitTemplate.convertAndSend(exchange, key, message);
    }


    void sendByConfirmMode(String exchange , String key, MessageDto messageDto, long timeSec) {
        //指定 ConfirmCallback
        rabbitTemplate.setConfirmCallback(this);
        //指定 ReturnCallback
        rabbitTemplate.setReturnCallback(this);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchange, key, messageDto, message -> {
            message.getMessageProperties().setExpiration(String.valueOf(timeSec*1000));
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        } , correlationData);
    }

    public abstract void  send(MessageDto messageDto) ;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("发送方确认 消息成功发送到exchange {} ", JSONObject.toJSONString(correlationData));
        } else {
            log.info("发送方确认 消息发送exchange失败 {} ", cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("发送方确认  消息从exchange到queue失败 {} {} {}",exchange , routingKey , replyText);
    }

}