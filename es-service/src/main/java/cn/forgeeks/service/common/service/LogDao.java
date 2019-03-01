package cn.forgeeks.service.common.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class LogDao {

    private final String INDEX = "sys_logs";
    private final String TYPE = "sysLog";

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    public void getInfo(){
        log.info("搜索引擎信息：[{}]" , restHighLevelClient.toString());
        log.info("搜索引擎索引信息：[{}]" , restHighLevelClient.indices());
        log.info("搜索引擎集群信息：[{}]" , restHighLevelClient.cluster());
    }

    /**
     * 根据traceId查询LOG记录
     * @param traceId
     * @return
     * @throws IOException
     */
    public void getLogByTraceId(String traceId) throws IOException {
        List  result = new ArrayList();

        // term query, 查询trace字段
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("trace", traceId));
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        // 指定索引和类型
        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.types(TYPE);
        searchRequest.source(sourceBuilder);

        // 执行查询
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        SearchHits hits = searchResponse.getHits();

        // 查询结果
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String timestamp = (String) sourceAsMap.get("@timestamp");
            String trace = (String) sourceAsMap.get("trace");
            String service = (String) sourceAsMap.get("service");
            String level = (String) sourceAsMap.get("severity");
            Object stackTrace = sourceAsMap.get("stack_trace");
            String stack_trace = null;
            if (stackTrace != null) {
                stack_trace = (String) stackTrace;
            }
            log.info("1");

        }
    }

}