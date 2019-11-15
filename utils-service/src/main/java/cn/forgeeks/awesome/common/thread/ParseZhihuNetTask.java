package cn.forgeeks.awesome.common.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class ParseZhihuNetTask implements Runnable {
  @Override
  public void run() {
    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(10));
    log.info("### aaa");
  }
}
