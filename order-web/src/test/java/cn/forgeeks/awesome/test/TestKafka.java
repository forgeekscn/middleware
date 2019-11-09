package cn.forgeeks.awesome.test;

import cn.forgeeks.awesome.kafka.chen.common.message.PayMessage;
import cn.forgeeks.awesome.kafka.chen.common.util.ToolsUtil;
import cn.forgeeks.awesome.kafka.chen.producer.MessageProducer;
import cn.forgeeks.awesome.order.MainApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT , classes = MainApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("home")
@Slf4j
public class TestKafka {

    @Autowired
    MessageProducer kafkaSender;

    @Test
    public void test(){
        PayMessage message = new PayMessage();
        message.setFee(ToolsUtil.getFee());
        message.setOrderCode(ToolsUtil.getNextCode());
        message.setSendTime(System.currentTimeMillis());
        kafkaSender.send(message);
        LockSupport.parkNanos(TimeUnit.MICROSECONDS,1);
    }

}
