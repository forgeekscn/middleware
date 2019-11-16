package cn.forgeeks.awesome.common.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class PersmitLock {

  private Lock lock;

  public PersmitLock() {
    this.lock = new ReentrantLock();
  }

  /**
   * fair & unfair reentran lock
   */
  public PersmitLock(boolean isFair) {
    this.lock = new ReentrantLock(isFair);
  }

  public boolean tryLock() {

    try {
      boolean isSueccess = lock.tryLock(1L, TimeUnit.SECONDS);
      return isSueccess;
    } catch (InterruptedException e) {
      log.warn("### trylcok failed ", e);
    }
    return false;
  }

  public void unlock() {
    lock.unlock();
  }
}
