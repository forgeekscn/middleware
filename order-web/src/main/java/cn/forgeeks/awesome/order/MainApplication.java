package cn.forgeeks.awesome.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class ,
        DataSourceTransactionManagerAutoConfiguration.class,
//        KafkaAutoConfiguration.class,
        RedisAutoConfiguration.class ,
        RabbitAutoConfiguration.class,
        ElasticsearchAutoConfiguration.class },
        scanBasePackages = {
//        "cn.forgeeks.awesome.redis.common.spconf.com.config",
                "cn.forgeeks.awesome.util",
                "cn.forgeeks.awesome.kafka",
//        "cn.forgeeks.awesome.mq",
//        "cn.forgeeks.awesome.es",
                "cn.forgeeks.awesome.order"
//        "cn.forgeeks.awesome"
        }
)
@ComponentScan({
//        "cn.forgeeks.awesome.redis.common.spconf.com.config",
        "cn.forgeeks.awesome.util",
        "cn.forgeeks.awesome.kafka",
//        "cn.forgeeks.awesome.mq",
//        "cn.forgeeks.awesome.es",
        "cn.forgeeks.awesome.order"
//        "cn.forgeeks.awesome"
})
//@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}

//@Import(FdfsClientConfig.class)
