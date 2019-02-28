package cn.forgeeks.service.common.common;

import cn.forgeeks.service.common.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMailSender {

    private static final Logger log = LoggerFactory.getLogger(RabbitMailSender.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 生产邮件消息
     */
    public void sendMail(MessageDto mail){
        rabbitTemplate.convertAndSend(Consts.EXCHANGE_MYTH_TOPIC  ,Consts.QUEUE_MYTH_MAIL_ALL , mail );
        log.info("邮件生产者[{}]",mail);
    }

}
