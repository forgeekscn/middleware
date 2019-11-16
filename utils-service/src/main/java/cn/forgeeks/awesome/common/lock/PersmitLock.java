package cn.forgeeks.awesome.common.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
public class PersmitLock {



    private Lock lock ;

    public PersmitLock(){
        this.lock = new ReentrantLock();
    }

    public void tryLock(){

        try {
            lock.tryLock(5L , TimeUnit.SECONDS);
            log.info("### try lcok success");
        } catch (InterruptedException e) {
            log.warn("### trylcok failed ",e);
        }
    }

    public void unlock(){
        lock.unlock();
        log.info("### unlock success");
    }


}
