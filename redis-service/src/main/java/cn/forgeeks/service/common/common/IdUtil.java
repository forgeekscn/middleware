package cn.forgeeks.service.common.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 分布式ID生成器-redis实现
 */
@Component
public class IdUtil {

    private static final String KEY_ELASTIC_PRODUCT_ID = "KEY_ELASTIC_PRODUCT_ID";

    @Autowired
    RedisUtil redisUtil;

    /*
     * 获取独立的id
     */
    public long getProductIdFromRedis(){
        if(redisUtil.hasKey(KEY_ELASTIC_PRODUCT_ID)){
            return redisUtil.incr(KEY_ELASTIC_PRODUCT_ID , 1);
        }else{
            redisUtil.set(KEY_ELASTIC_PRODUCT_ID,1000000);
            return getProductIdFromRedis();
        }
    }

}
