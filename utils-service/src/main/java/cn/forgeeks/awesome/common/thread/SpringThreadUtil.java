package cn.forgeeks.awesome.common.thread;

import cn.forgeeks.awesome.common.util.format.BigDecimalFomatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class SpringThreadUtil {

  public static ThreadPoolTaskExecutor generateDefaultThreadPool() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

    taskExecutor.setCorePoolSize(20);

    taskExecutor.setMaxPoolSize(100);

    taskExecutor.setKeepAliveSeconds(60);

    taskExecutor.setQueueCapacity(1000);

    taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

    taskExecutor.initialize();

    return taskExecutor;
  }

  public static ThreadPoolTaskExecutor submitTask(List<Runnable> tasks) {
    return submitTask(tasks, 60);
  }

  public static ThreadPoolTaskExecutor submitTask(List<Runnable> tasks, int timeOutSecond) {
    long startTime = System.currentTimeMillis();
    ThreadPoolTaskExecutor pool = generateDefaultThreadPool();

    log.info("### threadpool executor inited with timeout {}s ", timeOutSecond);

    try {
      tasks.forEach(pool::submit);
      while(timeOutSecond>0 && pool.getActiveCount()>0){
        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(1000));
        timeOutSecond--;
      }
      pool.shutdown();
    } catch (Exception e) {
      log.error("### error", e);
    } finally {
      pool.destroy();
    }

    long endTime = System.currentTimeMillis();
    double duration = BigDecimalFomatUtil.getFormatDouble((endTime - startTime) / 1.0);

    log.info("### tasks finished in {} ms ", duration);
    return pool;
  }
}
