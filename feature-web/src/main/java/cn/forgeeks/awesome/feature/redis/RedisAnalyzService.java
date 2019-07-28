package cn.forgeeks.awesome.feature.redis;

import cn.forgeeks.awesome.feature.dto.OrderDto;
import cn.forgeeks.awesome.feature.mapper.platform.OrderMapper;
import cn.forgeeks.awesome.redis.common.IdUtil;
import cn.forgeeks.awesome.redis.common.RedisUtil;
import cn.forgeeks.service.common.dto.ResultDto;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisAnalyzService {

    @Autowired
    @Qualifier("RedisUtil")
    RedisUtil redisUtil;

    @Autowired
    OrderMapper orderMapper;

    String ORDER_KEY = "ORDER_KEY_HASH";
    public ResultDto saveOrderToRedis(OrderDto orderDto){
        ResultDto rs = new ResultDto(400 , "参数有误");
        if(orderDto!=null&& orderDto.getId()!=null){
            String item = String.valueOf(orderDto.getId());
            Object value = redisUtil.hget(ORDER_KEY,item);
            if(value==null){
                if(redisUtil.hset(ORDER_KEY , item, orderDto , 1, TimeUnit.DAYS)){
                    log.info("### hash set success 1 day => {}", JSONObject.toJSONString(orderDto));
                    rs.setCode(200);
                    rs.setMsg("success");
                }
            }else{
                log.info("### hash exists => {}", JSONObject.toJSONString(orderDto));
            }
        }
        return rs;
    }

    public ResultDto saveOrderToDb(OrderDto param) {
        ResultDto rs = new ResultDto(400 , "参数有误");
        if(param!=null){
            Integer num = orderMapper.insertOrder(Arrays.asList(param));
            rs.setCode(200);
            rs.setMsg("insertOrder => "+num);
        }
        log.info("### saveOrderToDb => {} ",JSONObject.toJSONString(param));
        return rs;
    }

    private static ExecutorService pool = new ThreadPoolExecutor(10, 10, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100000),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public ResultDto saveOrderToDbInHighQps(OrderDto param) {
        Long startTime = System.currentTimeMillis();
        ResultDto rs = new ResultDto(400 , "参数有误");
        if(param!=null) {
            for(int time=0;time<100;time++){
             pool.submit(() -> {
                 List<OrderDto> list =  new ArrayList<>();
                 for(int i=0;i<param.getQps();i++){
                     OrderDto aa= new OrderDto();
                     aa.setOrderNo("NO-"+idUtil.getProductIdFromRedis());
                     aa.setOrderName("美团编号-"+aa.getOrderNo());
                     list.add(aa);
                 }
                 orderMapper.insertOrder(list);
                 log.info("### saveOrderToDbInHighQps => {} " );
             });
            }
        }

       waitForPool(startTime);
        return rs ;
    }

    private void waitForPool(Long startTime) {
        pool.shutdown();//只是不能再提交新任务，等待执行的任务不受影响
        try {
            boolean loop = true;
            do {    //等待所有任务完成
                loop = !pool.awaitTermination(1, TimeUnit.SECONDS);  //阻塞，直到线程池里所有任务结束
            } while(loop);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long endTime=System.currentTimeMillis();
        long a= endTime - startTime;
        log.info("#######  time => {}ms",a);
        pool = new ThreadPoolExecutor(10, 10, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100000),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Autowired
    IdUtil idUtil;

    public ResultDto saveOrderToRedisInHighQps(OrderDto param) {
        Long startTime = System.currentTimeMillis();
        ResultDto rs = new ResultDto(400 , "参数有误");
        if(param!=null) {
            for(int time=0;time<100;time++){
                pool.submit(() -> {
                    for(int i=0;i<param.getQps();i++){
                        OrderDto aa=new OrderDto();
                        aa.setId(idUtil.getProductIdFromRedis());
                        aa.setOrderNo("NO-"+aa.getId());
                        aa.setOrderName("美团订单-"+aa.getOrderNo());
                        aa.setCreateTime(new Date());
                        saveOrderToRedis(aa);
                        log.info("### saveOrderToRedisInHighQps => {} ",aa);
                    }
                });
            }
        }

        waitForPool(startTime);
        return rs ;
    }





}
