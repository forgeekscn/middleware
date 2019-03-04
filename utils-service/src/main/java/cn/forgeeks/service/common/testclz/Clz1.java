package cn.forgeeks.service.common.testclz;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Clz1 {
    static int param1 = 1;

    static {
        log.info("clz1 test2  [{}]", ++param1);
    }

    public Clz1() {
        log.info("clz1 constructor [{}]", ++param1);
    }

    public void test1() {
        log.info("clz1 test1  [{}]", ++param1);
    }


}