package cn.forgeeks.service.common.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ElasticSearchConfiguration extends AbstractFactoryBean {

    @Value("${elasticsearch.host}")
    private String host;

    private RestHighLevelClient restHighLevelClient;

    @Override
    public void destroy() throws Exception {
        if (restHighLevelClient != null) {
            restHighLevelClient.close();
            log.info("############     关闭一个搜索引擎连接    ############");
        }
    }

    @Override
    public Class<RestHighLevelClient> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    protected Object createInstance() {
        try {
            // 如果有多个节点，构建多个HttpHost
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(host, 9200, "http")));
        } catch (Exception e) {
            log.error("#####################  搜索引擎配置错误",e.getMessage());
        }
        log.info("########################  搜索引擎配置成功  ##############################");
        return restHighLevelClient;
    }
}