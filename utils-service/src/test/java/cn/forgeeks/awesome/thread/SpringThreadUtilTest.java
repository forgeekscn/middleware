package cn.forgeeks.awesome.thread;

import cn.forgeeks.awesome.stream.StreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class SpringThreadUtilTest {

    @Test
    public void testA(){

        List<Runnable> tasks= StreamUtil.getNatureNumbers(1000).stream().map(k->new ParseZhihuNetTask()).collect(Collectors.toList());

        ThreadPoolTaskExecutor pool = SpringThreadUtil.submitTask(tasks,60);

        assertNotNull(pool);

    }




}