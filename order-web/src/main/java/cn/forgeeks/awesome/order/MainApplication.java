package cn.forgeeks.awesome.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot service.
 */
//@Import(FdfsClientConfig.class)
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = "cn.forgeeks")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(cn.forgeeks.awesome.mq.MainApplication.class, args);
    }

}
