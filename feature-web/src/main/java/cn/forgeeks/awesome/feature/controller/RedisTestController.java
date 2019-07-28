package cn.forgeeks.awesome.feature.controller;

import cn.forgeeks.awesome.feature.dto.OrderDto;
import cn.forgeeks.awesome.feature.redis.RedisAnalyzService;
import cn.forgeeks.service.common.dto.ResultDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/redis")
@RestController
public class RedisTestController {

    @Autowired
    RedisAnalyzService redisAnalyzService;

    @ApiOperation("测试redis保存hash")
    @PostMapping("/saveOrder")
    public ResultDto saveOrder(@RequestBody OrderDto param){
        return redisAnalyzService.saveOrderToRedis(param);
    }

    @ApiOperation("测试order保存db")
    @PostMapping("/saveOrderToDb")
    public ResultDto saveOrderToDb(@RequestBody OrderDto param){
        return redisAnalyzService.saveOrderToDb(param);
    }

    @ApiOperation("测试高并发下 order保存db ")
    @PostMapping("/saveOrderToDbInHighQps")
    public ResultDto saveOrderToDbInHighQps(@RequestBody OrderDto param){
        return redisAnalyzService.saveOrderToDbInHighQps(param);
    }


    @ApiOperation("测试高并发下 order保存redis")
    @PostMapping("/saveOrderRedisInHighQps")
    public ResultDto saveOrderToRedisInHighQps(@RequestBody OrderDto param){
        return redisAnalyzService.saveOrderToRedisInHighQps(param);
    }



}
