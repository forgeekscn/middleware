package cn.forgeeks.awesome.common.lock;

import cn.forgeeks.awesome.common.stream.StreamUtil;
import cn.forgeeks.awesome.common.thread.SpringThreadUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class OptmisticLockTest {


    @Test
    public void test(){

        UserDto userDto = new UserDto(UUID.randomUUID().toString(),UUID.randomUUID().toString(), 20);
        AtomicReference<UserDto> userDtoAtomicReference =  OptmisticLock.get(userDto);

        SpringThreadUtil.submitTask( StreamUtil.getNatureNumbers(1000).stream().map(k->{
            Runnable runnable = () ->  userDtoAtomicReference.get().grow();
            return runnable;
        }).collect(Collectors.toList()));



        SpringThreadUtil.submitTask( StreamUtil.getNatureNumbers(1000).stream().map(k->{
            Runnable runnable = () ->  userDtoAtomicReference.get().grow();
            return runnable;
        }).collect(Collectors.toList()));



    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class UserDto {
        private String id;
        private String name;
        private Integer age;
        public UserDto grow(){age++;return this;}
        public UserDto young(){age--;return this;}
    }



    @Test
    public void testB(){

        BigDecimal bigDecimal = new BigDecimal("0.0054");
        System.out.println(bigDecimal.round(new MathContext(2,RoundingMode.HALF_UP)));
    }

}