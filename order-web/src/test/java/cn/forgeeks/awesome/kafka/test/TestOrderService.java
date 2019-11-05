//package cn.forgeeks.awesome.kafka.test;
//
//
//import cn.forgeeks.awesome.common.dto.MessageDto;
//import cn.forgeeks.awesome.mq.MainApplication;
//import cn.forgeeks.awesome.mq.common.config.MqConsts;
//import cn.forgeeks.awesome.mq.service.RabbitMqService;
//import cn.forgeeks.awesome.redis.common.RedisUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.Random;
//
//
//@Slf4j
//@SpringBootTest(classes = MainApplication.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles(value = "home")
//public class TestOrderService {
//
//    @Autowired
//    RedisUtil redisUtil;
//
//    @Autowired
//    RabbitMqService rabbitMqService;
//
////    @Autowired
////    ElasticService elasticService;
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//    @Autowired
//    private RestHighLevelClient client;
//
//    @Before
//    public void before() {
//        log.info("#########################  before    ##################################");
//    }
//
//    @After
//    public void after() {
//        log.info("#######################   after   ###########################");
//    }
//
//    @Test
//    public void redisTest() {
//        redisUtil.set("k1", "v1");
//        Object rs = redisUtil.get("k1");
//        log.info("k1 [{}]", rs);
//        redisUtil.set("num1", 1);
//        Object num1 = redisUtil.get("num1");
//        num1 = redisUtil.incr("num1", 1);
//        log.info("num1 [{}]", num1);
//    }
//
//    @Test
//    public void rabbitTest() {
//        MessageDto rs = rabbitMqService.sendAlllog();
//        log.info("rs [{}]", rs);
//    }
//
////    @Test
////    public void elasticTest() {
////         elasticService.getInfo();
////         elasticService.createIndex();
////        elasticService.clearIndex();
////        for (int i = 0; i <= 100; i++) {
////            elasticService.createDoc();
////        }
////        elasticService.queryPageByConditions();
////
////
////        // 测试复杂查询
////        JSON param = new JSONObject();
////        JSON rs = elasticService.matchQueryByIndex("order", param, 1, 10);
////    }
//
////    @Test
////    public void elasticTest2() throws IOException {
////        // term query(检索level)
////        TermsQueryBuilder levelQuery = null;
////        levelQuery = QueryBuilders.termsQuery("level", "info");
////
////        // match query(检索message)
////        MatchQueryBuilder messageQuery = null;
////        messageQuery = QueryBuilders.matchQuery("message", "工单");
////
////        // range query(检索timestamp)
////        RangeQueryBuilder timeQuery = QueryBuilders.rangeQuery("createTime");
////        // timeQuery.format("");
////        timeQuery.gte("2019-02-02T07:02:53.100+0000").lte("2019-05-02T07:02:53.148+0000");
////
////        // 将上述三个query组合
////        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
////
////        boolQuery.must(levelQuery).boost(1);
////        boolQuery.must(messageQuery).boost(2);
////        boolQuery.must(timeQuery).boost(3);
////
////        QueryBuilder query = boolQuery;
////
////        FieldSortBuilder order = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
////
////        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
////        searchBuilder.timeout(TimeValue.timeValueMinutes(2L));
////        searchBuilder.query(query);
////        searchBuilder.sort(order);
////        searchBuilder.from(0).size(10);
////
////        SearchRequest request = new SearchRequest("order");
////        request.source(searchBuilder);
////        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
////        SearchHits hits = response.getHits();
////        int totalRecord = (int) hits.getTotalHits();
////        List<Map<String, Object>> results = new ArrayList<>();
////        for (SearchHit hit : hits.getHits()) {
////            results.add(hit.getSourceAsMap());
////        }
////
////        Page<Map<String, Object>> page = new Page<>();
////        page.setPageNum(1);
////        page.setPageSize(10);
////        page.setTotalRecord(totalRecord);
////        page.setResults(results);
////
////        log.info("结果：[{}]", page);
////    }
//
//    @Test
//    public void rabbitmqTest() {
//        log.info("");
//        // rabbitMqService.sendAlllog();
//
//        MessageDto param = new MessageDto(new Random().nextInt(9999) + "", "和超", "12312312");
//        rabbitTemplate.convertAndSend(MqConsts.EXCHANGE_MYTH_TOPIC, MqConsts.QUEUE_MYTH_ORDER_ALL, param);
//    }
//
//
//}
