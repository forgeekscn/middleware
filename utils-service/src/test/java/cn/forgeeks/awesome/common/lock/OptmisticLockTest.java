package cn.forgeeks.awesome.common.lock;

import cn.forgeeks.awesome.common.stream.StreamUtil;
import cn.forgeeks.awesome.common.thread.SpringThreadUtil;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
public class OptmisticLockTest {



    @Test
    public void testA(){

        AtomicInteger num=new AtomicInteger(100);

        Runnable runnableA=()->num.incrementAndGet();
        Runnable runnableB=()->num.decrementAndGet();

        List<Runnable> listA =  StreamUtil.getNatureNumbers(1000).stream().map(k->runnableA).collect(Collectors.toList());
        List<Runnable> listB =  StreamUtil.getNatureNumbers(1000).stream().map(k->runnableB).collect(Collectors.toList());

        listA.addAll(listB);

        SpringThreadUtil.submitTask(listA);

        log.info("### {} ", num );

    }





    @Test
    public void testB(){

        UserDto userDto = new UserDto(UUID.randomUUID().toString(),UUID.randomUUID().toString(), 20,new AtomicInteger(100));
        AtomicReference<UserDto> userDtoAtomicReference =  OptmisticLock.get(userDto);

        Runnable runnableA = () ->  userDtoAtomicReference.get().grow();
        Runnable runnableB = () ->  userDtoAtomicReference.get().young();

        List<Runnable> listA =  StreamUtil.getNatureNumbers(1000).stream().map(k->runnableA).collect(Collectors.toList());
        List<Runnable> listB =  StreamUtil.getNatureNumbers(1000).stream().map(k->runnableB).collect(Collectors.toList());

        listA.addAll(listB);

        SpringThreadUtil.submitTask(listA);

        log.info("### {} ", JSON.toJSONString(userDtoAtomicReference));

    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDto {
        private String id;
        private String name;
        private Integer age;
        private AtomicInteger acount;
        public UserDto grow(){age++;acount.incrementAndGet();return this;}
        public UserDto young(){age--;acount.decrementAndGet();return this;}
    }





    @Test
    public void testC(){

        AtomicInteger a= new AtomicInteger();
    }




}