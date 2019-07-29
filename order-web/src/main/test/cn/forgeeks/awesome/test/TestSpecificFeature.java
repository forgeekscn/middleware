package cn.forgeeks.awesome.test;

import cn.forgeeks.awesome.order.RedisApp;
import cn.forgeeks.awesome.redis.common.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT , classes = RedisApp.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("work")
@Slf4j
public class TestSpecificFeature {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void test1(){
        redisUtil.set("a","aaa"+ new Random().nextInt(100));
        Object a = redisUtil.get("a");
        log.info("### rs => {}", JSONObject.toJSONString(a));
    }


}
