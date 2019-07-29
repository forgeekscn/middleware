package cn.forgeeks.awesome.common.language;

import cn.forgeeks.awesome.common.testclz.Clz2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Slf4j
public class TestLanguage {


    ExecutorService poll = Executors.newFixedThreadPool(20);
    ExecutorService poll2 = Executors.newFixedThreadPool(20);

    @Test
    public void test1() {
        log.info("###############   1.JAVA中的几种基本数据类型是什么，各自占用多少字节  ##################");
        int param1 = 1;
        long param2 = 2L;
        short param8 = 11;
        float param3 = 3F;
        double param4 = 4D;
        boolean param5 = true;
        char param6 = 'a';
        byte param7 = 'a';

        log.info("int [{}] [{}]", param1, Integer.SIZE);
        log.info("long [{}] [{}]", param2, Long.SIZE);
        log.info("short  [{}] [{}]", param8, Short.SIZE);
        log.info("float[{}] [{}]", param3, Float.SIZE);
        log.info("double [{}] [{}]", param4, Double.SIZE);
        log.info("boolean [{}] [{}]", param5, "1B");
        log.info("char [{}] [{}]", param6, Character.SIZE);
        log.info("byte  [{}] [{}]", param7, Byte.SIZE);

    }

    @Test
    public void test2() {
        log.info("########  String，Stringbuffer，StringBuilder的区别。   ##########");

        String param = new String();
        log.info("param [{}]", param.hashCode());
        param = new String("hello");
        log.info("param [{}]", param.hashCode());

        StringBuffer param2 = new StringBuffer();
        log.info("param2 [{}]", param2.hashCode());
        param2 = param2.append("hello");
        log.info("param2 [{}]", param2.hashCode());

        StringBuilder param3 = new StringBuilder();
        log.info("param3 [{}]", param3.hashCode());
        param3 = param3.append("hello");
        log.info("param3 [{}]", param3.hashCode());

    }

    @Test
    public void test3() {
        log.info("############   ArrayList和LinkedList有什么区别。  ##############");
        ArrayList param1 = new ArrayList();
        LinkedList param2 = new LinkedList();

        param2.push("a");
        param2.push("b");
        param2.push("c");
        log.info("peek [{}]", param2.peekFirst());
        log.info("peekFirst [{}]", param2.peekLast());
        log.info("pop [{}]", param2.pop());
        log.info("peekFirst [{}]", param2.peekLast());
        log.info("poll[{}]", param2.poll());
    }

    @Test
    public void test4() {
        log.info("讲讲类的实例化顺序，比如父类静态数据，构造函数，字段，子类静态数据，构造函数，字段，当new的时候，他们的执行顺序。");
//        Clz1 obj = new Clz1();
//        Clz1 obj = new Clz2();
        Clz2 obj = new Clz2();
        obj.test1();

    }

    /**
     * 用过哪些Map类，都有什么区别，HashMap是线程安全的吗,并发下使用的Map是什么，
     * 他们内部原理分别是什么，比如存储方式，hashcode，扩容，默认容量等。
     */
    @Test
    public void test5() throws InterruptedException {
        HashMap map1 = new HashMap();
        ConcurrentHashMap map2 = new ConcurrentHashMap();
        Integer i = 100000;
        while (i-- > 0) {
            int finalI = i;
            int finalI1 = i;
            poll.submit(() -> {
                map1.put(finalI, finalI1);
            });
        }
        poll.shutdown();

        while (!poll.awaitTermination(10, TimeUnit.SECONDS)) ;
        log.info("[{}]", map1.size());

        i = 100000;
        while (i-- > 0) {
            int finalI = i;
            int finalI1 = i;
            poll2.submit(() -> {
                map2.put(finalI, finalI1);
            });
        }
        poll2.shutdown();
        while (!poll2.awaitTermination(10, TimeUnit.SECONDS)) ;
        log.info("[{}]", map2.size());

    }

}
