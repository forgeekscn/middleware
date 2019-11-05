package cn.forgeeks.awesome.kafka;

import cn.forgeeks.awesome.kafka.chen.common.message.PayMessage;
import cn.forgeeks.awesome.kafka.chen.common.util.ToolsUtil;
import cn.forgeeks.awesome.kafka.chen.producer.MessageProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class },
        scanBasePackages = { "cn.forgeeks.awesome.kafka"  }
)
public class MainApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MainApplication.class, args);
        MessageProducer producer = applicationContext.getBean(MessageProducer.class);
        while (true){
            PayMessage message = new PayMessage();
            message.setFee(ToolsUtil.getFee());
            message.setOrderCode(ToolsUtil.getNextCode());
            message.setSendTime(System.currentTimeMillis());
            producer.send(message);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
