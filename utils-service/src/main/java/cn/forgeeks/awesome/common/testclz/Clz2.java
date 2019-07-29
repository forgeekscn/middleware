package cn.forgeeks.awesome.common.testclz;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Clz2 extends Clz1 {
    static {
        log.info("clz2 test2  [{}]", ++param1);
    }

    public Clz2() {
        log.info("clz2 constructor [{}]", ++param1);
    }

    @Override
    public void test1() {
        log.info("clz2 test1  [{}]", ++param1);
    }

}
