package cn.forgeeks.springcloud.memcached.common;

import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTests {

    Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private MemcachedRunner memcachedRunner;

    @Test
    public void testSetGet() {
        MemcachedClient memcachedClient = memcachedRunner.getClient();
        memcachedClient.set("testkey", 1000, "666666");
        log.info("***********  [{}]" , memcachedClient.get("testkey").toString());
    }

}