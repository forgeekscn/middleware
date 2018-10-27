package cn.forgeeks.springcloud.redis.controller;

import cn.forgeeks.springcloud.rabbitmq.dto.MessageDto;
import cn.forgeeks.springcloud.rabbitmq.dto.ResultDto;
import cn.forgeeks.springcloud.redis.common.RedisUtil;
import cn.forgeeks.springcloud.redis.dto.RedisQueueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/redis")
public class JedisController {

    @Autowired
    RedisUtil redisUtil;

    @PostMapping(value = "/selectAll")
    public ResultDto sendMsg(@RequestBody RedisQueueDto queueDto){
        boolean hasKey = redisUtil.hasKey(queueDto.getKey());

        Map map = new HashMap();
        MessageDto messageDto=new MessageDto();
        messageDto.setId(UUID.randomUUID().toString());
        messageDto.setTitle("Here is my ttle.");
        map.put(messageDto.getId(),messageDto);
        redisUtil.hmset("message_info_set",map);
        Map mapRs = redisUtil.hmget("message_info_set");

        return new ResultDto(200, mapRs);
    }



}
