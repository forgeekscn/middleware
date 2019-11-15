package cn.forgeeks.awesome.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class SpringThreadUtil {

    public static ThreadPoolTaskExecutor generateDefaultThreadPool(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(20);

        taskExecutor.setMaxPoolSize(10000);

        taskExecutor.setKeepAliveSeconds(60);

        taskExecutor.setQueueCapacity(100000);

        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        taskExecutor.initialize();

        return taskExecutor;
    }



    public static ThreadPoolTaskExecutor submitTask(List<Runnable> tasks, long timeOutSecond ){
        ThreadPoolTaskExecutor pool = generateDefaultThreadPool();

        tasks.forEach(pool::submit);

        try {
            pool.getThreadPoolExecutor().awaitTermination(timeOutSecond, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("### error" , e);
        }finally{
            pool.destroy();
        }
        log.info("### task finished in {} seconds " , timeOutSecond);
        return pool;
    }





}
