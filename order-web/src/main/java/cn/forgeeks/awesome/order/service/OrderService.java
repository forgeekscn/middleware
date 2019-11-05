package cn.forgeeks.awesome.order.service;

import cn.forgeeks.awesome.kafka.common.KafkaSender;
import cn.forgeeks.awesome.mq.common.sender.RabbitLogSender;
import cn.forgeeks.awesome.order.mapper.log.OrderLogMapper;
import cn.forgeeks.awesome.order.mapper.platform.UserMapper;
import cn.forgeeks.awesome.redis.common.config.util.hash.HashRedisUtil;
import cn.forgeeks.awesome.redis.common.spconf.com.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class OrderService {

    @Autowired
    RedisService redisUtil;

    @Autowired
    RabbitLogSender rabbitLogSender;

    @Autowired(required = false)
    KafkaSender kafkaSender;

    @Autowired
    OrderLogMapper orderLogMapper;

    @Autowired
    UserMapper userMapper;

    public void test(){
        Object obj = redisUtil.getKeyExpire("k1", TimeUnit.MINUTES);
        log.info("### redisUtil test [{}]",obj);
//        kafkaSender.send();
        log.info("### kafkaSender test [{}]",obj);

    }


    public Integer getOrderLogCount(){
        return orderLogMapper.getOrderLogCount("");
    }

    public Integer getUserCount(){
        return userMapper.getUserCount("");
    }
}
