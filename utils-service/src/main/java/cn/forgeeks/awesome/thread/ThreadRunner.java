package cn.forgeeks.awesome.thread;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 线程运行demo，运行时打出线程id以及传入线程中参数
 */
@Slf4j
public class ThreadRunner implements Runnable {

    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");

    /**
     * 线程私有属性，创建线程时创建
     */
    private Integer num;

    public ThreadRunner(Integer num) {
        this.num = num;
    }

    @Override
    public void run() {
        log.info("### thread:{} ,time:{} ,num:{}",
                Thread.currentThread().getName(), format.format(new Date()) , num);
        try {
            //使线程睡眠，模拟线程阻塞情况
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}