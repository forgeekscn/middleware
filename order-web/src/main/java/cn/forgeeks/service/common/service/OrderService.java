package cn.forgeeks.service.common.service;

import cn.forgeeks.service.common.common.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @Autowired
    RedisUtil redisUtil;

    public void test(){
        Object obj = redisUtil.get("k1");
        log.info("common get value [{}]",obj);
    }


}
