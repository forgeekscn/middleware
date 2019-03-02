package cn.forgeeks.test.service;


import cn.forgeeks.service.common.MainApplication;
import cn.forgeeks.service.common.common.RedisUtil;
import cn.forgeeks.service.common.dto.MessageDto;
import cn.forgeeks.service.common.service.ElasticService;
import cn.forgeeks.service.common.service.Page;
import cn.forgeeks.service.common.service.RabbitMqService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "home")
public class TestOrderService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RabbitMqService rabbitMqService;

    @Autowired
    ElasticService elasticService;

    @Autowired
    private RestHighLevelClient client;

    @Before
    public void before() {
        log.info("#########################  before    ##################################");
    }

    @After
    public void after() {
        log.info("#######################   after   ###########################");
    }

    @Test
    public void redisTest() {
        redisUtil.set("k1", "v1");
        Object rs = redisUtil.get("k1");
        log.info("k1 [{}]", rs);
        redisUtil.set("num1", 1);
        Object num1 = redisUtil.get("num1");
        num1 = redisUtil.incr("num1", 1);
        log.info("num1 [{}]", num1);
    }

    @Test
    public void rabbitTest() {
        MessageDto rs = rabbitMqService.sendAlllog();
        log.info("rs [{}]", rs);
    }

    @Test
    public void elasticTest() {
        // elasticService.getInfo();
        // elasticService.createIndex();
//        elasticService.clearIndex();
//        for (int i = 0; i <= 100; i++) {
//            elasticService.createDoc();
//        }
//        elasticService.queryPageByConditions();


        // 测试复杂查询
        JSON param = new JSONObject();
        JSON rs = elasticService.matchQueryByIndex("order", param, 1, 10);
    }

    @Test
    public void elasticTest2() throws IOException {
        // term query(检索level)
        TermsQueryBuilder levelQuery = null;
        levelQuery = QueryBuilders.termsQuery("level", "info");

        // match query(检索message)
        MatchQueryBuilder messageQuery = null;
        messageQuery = QueryBuilders.matchQuery("message", "工单");

        // range query(检索timestamp)
        RangeQueryBuilder timeQuery = QueryBuilders.rangeQuery("createTime");
        // timeQuery.format("");
        timeQuery.gte("2019-03-02T07:02:53.100+0000").lte("2019-03-02T07:02:53.148+0000");

        // 将上述三个query组合
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.must(levelQuery).boost(1);
        boolQuery.must(messageQuery).boost(2);
        boolQuery.must(timeQuery).boost(3);

        QueryBuilder query = boolQuery;

        FieldSortBuilder order = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);

        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.timeout(TimeValue.timeValueMinutes(2L));
        searchBuilder.query(query);
        searchBuilder.sort(order);
        searchBuilder.from(0).size(10);

        SearchRequest request = new SearchRequest("order");
        request.source(searchBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        int totalRecord = (int) hits.getTotalHits();
        List<Map<String, Object>> results = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            results.add(hit.getSourceAsMap());
        }

        Page<Map<String, Object>> page = new Page<>();
        page.setPageNum(1);
        page.setPageSize(10);
        page.setTotalRecord(totalRecord);
        page.setResults(results);

        log.info("结果：[{}]", page);
    }


}
