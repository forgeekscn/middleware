package cn.forgeeks.awesome.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

/**
     干净的启动方式
     exclude = {DataSourceAutoConfiguration.class , RedisAutoConfiguration.class, KafkaAutoConfiguration.class,
     RabbitAutoConfiguration.class , ElasticsearchAutoConfiguration.class }
 */
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class , KafkaAutoConfiguration.class,
//        RedisAutoConfiguration.class , ElasticsearchAutoConfiguration.class },
//        scanBasePackages = { "cn.forgeeks.awesome.mq.common"  }
//)
public class RabbitApp {
    public static void main(String[] args) {
        SpringApplication.run(RabbitApp.class);
    }
}
