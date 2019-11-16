package cn.forgeeks.awesome.common.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
public class OptmisticLock  {

    public static  <T> AtomicReference get(T obj){
        return new AtomicReference(obj);
    }

    public static  AtomicInteger get(int intValue){
        return  new AtomicInteger(intValue);
    }

}
