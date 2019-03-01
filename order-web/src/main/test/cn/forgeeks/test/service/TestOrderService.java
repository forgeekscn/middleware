package cn.forgeeks.test.service;


import cn.forgeeks.service.common.MainApplication;
import cn.forgeeks.service.common.common.RedisUtil;
import cn.forgeeks.service.common.dto.MessageDto;
import cn.forgeeks.service.common.service.ElasticService;
import cn.forgeeks.service.common.service.RabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@Slf4j
@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "home")
public class TestOrderService {

    @Autowired
    RedisUtil  redisUtil;

    @Autowired
    RabbitMqService rabbitMqService;

    @Autowired
    ElasticService elasticService;

    @Before
    public void before(){
        log.info("#########################  before    ##################################");
    }

    @After
    public void after(){
        log.info("#######################   after   ###########################");
    }


    @Test
    public void redisTest(){
        redisUtil.set("k1","v1");
        Object rs = redisUtil.get("k1");
        log.info("k1 [{}]",rs);
        redisUtil.set("num1",1);
        Object num1 =  redisUtil.get("num1");
        num1 = redisUtil.incr("num1",1);
        log.info("num1 [{}]",num1);
    }

    @Test
    public void rabbitTest(){
        MessageDto rs = rabbitMqService.sendAlllog();
        log.info("rs [{}]",rs);
    }


    @Test
    public void elasticTest(){
        elasticService.getInfo();

    }


}
