package cn.forgeeks.springcloud.schedul.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {
    Logger log = LoggerFactory.getLogger(getClass());
    private int count=0;

    @Scheduled(cron="*/5 * * * * ?")
    private void process(){
        log.info("This is scheduler task runing  [{}]." , (count++));
    }

}