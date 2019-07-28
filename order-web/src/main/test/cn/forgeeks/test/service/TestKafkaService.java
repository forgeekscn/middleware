package cn.forgeeks.test.service;


import cn.forgeeks.awesome.kafka.common.KafkaSender;
import cn.forgeeks.service.common.MainApplication;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//import cn.forgeeks.service.common.service.ElasticService;
//import cn.forgeeks.service.common.service.Page;


@Slf4j
@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "home")
public class TestKafkaService {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    private RestHighLevelClient client;

    @Before
    public void before() {
        log.info("#########################  before    ##################################");
    }

    @After
    public void after() {
        log.info("#######################   after   ###########################");
    }

    @Test
    public void kafkaTest() {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, "");
        KafkaSender sender = context.getBean(KafkaSender.class);
        for (int i = 0; i < 3; i++) {
            sender.send();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
