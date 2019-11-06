package cn.forgeeks.awesome.test;

import cn.forgeeks.awesome.common.dto.MessageDto;
import cn.forgeeks.awesome.es.common.ElasticService;
import cn.forgeeks.awesome.kafka.chen.common.message.PayMessage;
import cn.forgeeks.awesome.kafka.chen.common.util.ToolsUtil;
import cn.forgeeks.awesome.kafka.chen.producer.MessageProducer;
import cn.forgeeks.awesome.mq.common.sender.RabbitOrderSender;
import cn.forgeeks.awesome.order.MainApplication;
import cn.forgeeks.awesome.order.mapper.log.OrderLogMapper;
import cn.forgeeks.awesome.redis.common.spconf.com.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT , classes = MainApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("home")
@Slf4j
public class TestSpecificFeature {


    @Autowired
    MessageProducer kafkaSender;

    @Autowired
    RedisService    redisService;

    @Autowired
    OrderLogMapper  orderLogMapper;

    @Autowired
    RabbitOrderSender rabbitOrderSender;

    @Autowired
    ElasticService elasticService;

    @Test
    public void test(){
        PayMessage message = new PayMessage();
        message.setFee(ToolsUtil.getFee());
        message.setOrderCode(ToolsUtil.getNextCode());
        message.setSendTime(System.currentTimeMillis());
        kafkaSender.send(message);
    }

    @Test
    public void testb(){
        rabbitOrderSender.sendOrderMsg(new MessageDto());
    }


    @Test
    public void testc(){
        redisService.existsKey("a");
    }

    @Test
    public void testd(){
        orderLogMapper.getOrderLogCount("2019-11-06");
    }


    @Test
    public void teste(){
        elasticService.getInfo();
    }

//    @Autowired
//    RedisUtil redisUtil;
//
//    @Test
//    public void test1(){
//        redisUtil.set("a","aaa"+ new Random().nextInt(100));
//        Object a = redisUtil.get("a");
//        log.info("### rs => {}", JSONObject.toJSONString(a));
//    }
//
//    @Test
//    public void test2(){
//        String key="KEY_MAP_HMSET_TEST";
//        Map<String,Object> map =new HashMap<>();
//        map.put("id",123);
//        map.put("name","何超");
//        redisUtil.hmset(key,map);
//
//        Map rs  = redisUtil.hmget(key);
//        log.info("### {}",rs);
//
//        String key2 = "KE_HASH_ORDER";
//        String item = "id";
//        ResultDto obj = new ResultDto(200,"陈功");
//        redisUtil.hset(key2,"id_01",JSONObject.toJSONString(obj));
//        redisUtil.hset(key2,"id_02",JSONObject.toJSONString(obj));
//
//        String rs2 = String.valueOf(redisUtil.hget(key2,"id_01"));
//        log.info("### {}",rs2);
//        List list =new ArrayList();
//        while(true){
//            String key3 = null+"";
//            list.add(key3);
//            if(key3==null) break;
//        }
//
//    }

}
