package cn.forgeeks.awesome.order.service;

import cn.forgeeks.awesome.kafka.common.KafkaSender;
import cn.forgeeks.awesome.mq.common.RabbitLogSender;
import cn.forgeeks.awesome.order.mapper.log.OrderLogMapper;
import cn.forgeeks.awesome.order.mapper.platform.UserMapper;
import cn.forgeeks.awesome.redis.common.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RabbitLogSender rabbitLogSender;

    @Autowired(required = false)
    KafkaSender kafkaSender;

    @Autowired
    OrderLogMapper orderLogMapper;

    @Autowired
    UserMapper userMapper;

    public void test(){
        Object obj = redisUtil.get("k1");
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
