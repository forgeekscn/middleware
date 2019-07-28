package cn.forgeeks.awesome.redis.common;

import cn.forgeeks.service.common.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMailSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 生产邮件消息
     */
    public void sendMail(MessageDto mail) {
        rabbitTemplate.convertAndSend(Consts.EXCHANGE_MYTH_TOPIC, Consts.QUEUE_MYTH_MAIL_ALL, mail);
        log.info("邮件生产者[{}]", mail);
    }

}
