package cn.forgeeks.service.common.common;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket testApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("rabbit")
                .genericModelSubstitutes(DeferredResult.class)
//                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/rabbit/.*")))//过滤的接口
                .build()
                .apiInfo(testRabbitApiInfo());
    }

    private ApiInfo testRabbitApiInfo() {
        return new ApiInfoBuilder()
            .title("rabbitmq测试接口")//大标题
            .description("rabbitmq测试接口")//详细描述
            .version("1.0")//版本
            .termsOfServiceUrl("NO terms of service")
//            .contact(new Contact("小单", "http://blog.csdn.net/catoop", "365384722@qq.com"))//作者
//            .license("The Apache License, Version 2.0")
//            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .build();
    }



    @Bean
    public Docket testApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("datasource")
                .genericModelSubstitutes(DeferredResult.class)
//                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/datasource/.*")))//过滤的接口
                .build()
                .apiInfo(testRabbitApiInfo2());
    }

    private ApiInfo testRabbitApiInfo2() {
        return new ApiInfoBuilder()
                .title("datasource测试接口")//大标题
                .description("datasource测试接口")//详细描述
                .version("1.0")//版本
                .termsOfServiceUrl("NO terms of service")
//                .license("The Apache License, Version 2.0")
//                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }


}