package cn.forgeeks.awesome.test;


import cn.forgeeks.awesome.common.dto.MessageDto;
import cn.forgeeks.awesome.mq.common.sender.RabbitDeadLetterSender;
import cn.forgeeks.awesome.order.RabbitApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT , classes = RabbitApp.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("home")
@Slf4j
public class TestMqDealetterSpecific {


    @Autowired
    RabbitDeadLetterSender deadLetterSender;
    @Test
    public void test1(){
        MessageDto messageDto = new MessageDto();
        messageDto.setContent("淘宝订单1111111");
        deadLetterSender.send(messageDto);
        try {
            Thread.sleep(100*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
