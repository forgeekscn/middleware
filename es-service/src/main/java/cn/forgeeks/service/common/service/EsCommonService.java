package cn.forgeeks.service.common.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EsCommonService{

    public Logger log = LoggerFactory.getLogger(EsCommonService.class);

    @Autowired
    private TransportClient client;


    /**
     * 查看集群信息
     */
    public void getClusterInfo() {
        List<DiscoveryNode> nodes = client.connectedNodes();
        for (DiscoveryNode node : nodes) {
            log.info(""+"HostId:"+node.getHostAddress()+" hostName:"+node.getHostName()+" Address:"+node.getAddress());
        }
    }

    /**
     * 功能描述：新建索引
     */
    public  void createIndex(String indexName) {
        try {
            if (indexExist(indexName)) {
                log.info(""+"The index " + indexName + " already exits!");
            } else {
                CreateIndexResponse cIndexResponse = client.admin().indices()
                        .create(new CreateIndexRequest(indexName))
                        .actionGet();
                if (cIndexResponse.isAcknowledged()) {
                    log.info(""+"create index successfully！");
                } else {
                    log.info(""+"Fail to create index!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述：新建索引
     * @param index 索引名
     * @param type 类型
     */
    public  void createIndex(String index, String type) {
        try {
            client.prepareIndex(index, type).setSource().get();
        } catch (Exception e) {
            log.info(""+"Fail to create index!");
            e.printStackTrace();
        }
    }


    /**
     * 功能描述：删除索引
     * @param index 索引名
     */
    public  void dropIndex(String index) {
        try {
            if (indexExist(index)) {
                DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index)
                        .execute().actionGet();
                if (!dResponse.isAcknowledged()) {
                    log.info(""+"failed to delete index " + index + "!");
                }else {
                    log.info(""+"delete index " + index + " successfully!");
                }
            } else {
                log.error("the index " + index + " not exists!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述：清空索引
     * @param index 索引名
     */
    public void deleteIndex(String index ){
        client.admin().indices().delete(new DeleteIndexRequest(index)).actionGet().isAcknowledged();
    }

    /**
     * 功能描述：验证索引是否存在
     * @param index 索引名
     */
    public  boolean indexExist(String index) {
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(index);
        IndicesExistsResponse inExistsResponse = client.admin().indices()
                .exists(inExistsRequest).actionGet();
        return inExistsResponse.isExists();
    }

    /**
     * 功能描述：插入数据
     * @param index 索引名
     * @param type 类型
     * @param json 数据
     */
    @SuppressWarnings("deprecation")
    public  void insertData(String index, String type, String json) {
        IndexResponse response = client.prepareIndex(index, type).setSource(json).get();
        log.info(""+""+response.getVersion());
    }

    /**
     * 功能描述：获取所有的索引
     */
    public  void getAllIndex(){
        ClusterStateResponse response = client.admin().cluster().prepareState().execute().actionGet();
        //获取所有索引
        String[] indexs=response.getState().getMetaData().getConcreteAllIndices();
        log.info(""+"Index总数为: " + indexs.length);
        for (String index : indexs) {
            log.info(""+"获取的Index: " + index);
        }
    }

    /**
     * 通过prepareIndex增加文档，参数为json字符串
     * @param index 索引名
     * @param type  类型
     * @param _id   数据id
     * @param json  数据
     */
    @SuppressWarnings("deprecation")
    public  void insertData(String index, String type, String _id, String json) {
        IndexResponse indexResponse = client.prepareIndex(index, type).setId(_id)
                .setSource(json)
                .get();
        log.info(""+indexResponse.getVersion());
        log.info(""+"数据插入ES成功！");
    }

    /**
     * 功能描述：更新数据
     * @param index 索引名
     * @param type  类型
     * @param _id   数据id
     * @param json  数据
     */
    @SuppressWarnings("deprecation")
    public  void updateData(String index, String type, String _id, String json){
        try {
            UpdateRequest updateRequest = new UpdateRequest(index, type, _id).doc(json);
//            client.prepareUpdate(index, type, _id).setDoc(json).get();
            client.update(updateRequest).get();
        } catch (Exception e) {
            log.error("update data failed." + e.getMessage());
        }
    }

    /**
     * 功能描述：删除指定数据
     * @param index 索引名
     * @param type  类型
     * @param _id   数据id
     */
    public  void deleteData(String index, String type, String _id) {
        try {
            DeleteResponse response = client.prepareDelete(index, type, _id).get();
            log.info(""+response.isFragment());
            log.info(""+"删除指定数据成功！");
        } catch (Exception e) {
            log.error("删除指定数据失败！" + e);
        }
    }

    /**
     * 删除索引类型表所有数据，批量删除
     * @param index
     * @param type
     */
    public  void deleteIndexTypeAllData(String index, String type) {
        SearchResponse response = client.prepareSearch(index).setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setScroll(new TimeValue(60000)).setSize(10000).setExplain(false).execute().actionGet();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        while (true) {
            SearchHit[] hitArray = response.getHits().getHits();
            SearchHit hit = null;
            for (int i = 0, len = hitArray.length; i < len; i++) {
                hit = hitArray[i];
                DeleteRequestBuilder request = client.prepareDelete(index, type, hit.getId());
                bulkRequest.add(request);
            }
            BulkResponse bulkResponse = bulkRequest.execute().actionGet();
            log.info("删除es数据[{}]条",hitArray.length);
            if (bulkResponse.hasFailures()) {
                log.error(bulkResponse.buildFailureMessage());
            }
            if(hitArray.length == 0) {
                break;
            }
            response = client.prepareSearchScroll(response.getScrollId())
                    .setScroll(new TimeValue(60000)).execute().actionGet();
        }
    }

    /**
     * 功能描述：批量插入数据
     * @param index    索引名
     * @param type     类型
     * @param jsonList 批量数据
     */
    @SuppressWarnings("deprecation")
    public void bulkInsertData(String index, String type, List<String> jsonList) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        jsonList.forEach(item -> {
            bulkRequest.add(client.prepareIndex(index, type).setSource(item));
        });
        BulkResponse bulkResponse = bulkRequest.get();
        if(!bulkResponse.hasFailures()) {
            log.info(""+bulkResponse.getItems().length + "条数据插入完成！");
        }
    }

    /**
     * 通过prepareGet方法获取指定文档信息
     */
    public  void getOneDocument(String index, String type, String id) {
        // 搜索数据
        GetResponse response = client.prepareGet(index, type, id)
//                .setOperationThreaded(false)    // 线程安全
                .get();
        log.info(""+response.isExists());  // 查询结果是否存在
        log.info(""+"***********"+response.getSourceAsString());//获取文档信息
//        log.info(""+response.toString());//获取详细信息
    }

    /**
     * 通过prepareSearch方法获取指定索引所有文档信息
     */
    public  List<Map<String, Object>> getDocuments(String index) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        // 搜索数据
        SearchResponse response = client.prepareSearch(index)
//    			.setTypes("type1","type2"); //设置过滤type
//    			.setTypes(SearchType.DFS_QUERY_THEN_FETCH)  精确查询
//    			.setQuery(QueryBuilders.matchQuery(term, queryString));
//    			.setFrom(0) //设置查询数据的位置,分页用
//    			.setSize(60) //设置查询结果集的最大条数
//    			.setExplain(true) //设置是否按查询匹配度排序
                .get(); //最后就是返回搜索响应信息
        log.info(""+"共匹配到:"+response.getHits().getTotalHits()+"条记录!");
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSource();
            mapList.add(source);
        }
        log.info(""+response.getTotalShards());//总条数
        return mapList;
    }

    /**
     * 获取指定索引库下指定type所有文档信息
     * @param index
     * @param type
     * @return
     */
    public  List<Map<String, Object>> getDocuments(String index, String type) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        SearchResponse response = client.prepareSearch(index).setTypes(type).get();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSource();
            mapList.add(source);
        }
        return mapList;
    }

    /**
     * 带有搜索条件的聚合查询（聚合相当于关系型数据库里面的group by）
     * @param index
     * @param type
     * @return
     */
    public  Map<String, Long> searchBucketsAggregation(String index, String type) {
        long total = 0;
        Map<String, Long> rtnMap = new HashMap<>();
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
        //搜索条件
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        /*queryBuilder.must(QueryBuilders.matchQuery("source", "resource"))精确匹配*/
        queryBuilder.filter(QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("logTime")
//                        .gte(dateStr + "00:00:00")
//                        .lte(dateStr + "23:59:59") //时间为当天
                        .format("yyyy-MM-dd HH:mm:ss")));//时间匹配格式
        // 获取当日告警总数
//        long total = searchRequestBuilder.setQuery(queryBuilder).get().getHits().getTotalHits();
        // 聚合分类（以告警类型分类）
        TermsAggregationBuilder teamAggBuilder = AggregationBuilders.terms("source_count").field("source.keyword");
        searchRequestBuilder.addAggregation(teamAggBuilder);
        // 不指定 "size":0 ，则搜索结果和聚合结果都将被返回,指定size：0则只返回聚合结果
        searchRequestBuilder.setSize(0);
        searchRequestBuilder.setQuery(queryBuilder);
        SearchResponse response = searchRequestBuilder.execute().actionGet();

//    	log.info(""+"++++++聚合分类："+response.toString());

        // 聚合结果处理
        Terms genders = response.getAggregations().get("source_count");
        for (Terms.Bucket entry : genders.getBuckets()) {
            Object key = entry.getKey();      // Term
            Long count = entry.getDocCount(); // Doc count

            rtnMap.put(key.toString(), count);

//            log.info(""+"Term: "+key);
//            log.info(""+"Doc count: "+count);
        }
        if(!rtnMap.isEmpty()) {
            if(!rtnMap.containsKey("system")) {
                rtnMap.put("system", 0L);
            }
            if(!rtnMap.containsKey("resource")) {
                rtnMap.put("resource", 0L);
            }
            if(!rtnMap.containsKey("scheduler")) {
                rtnMap.put("scheduler", 0L);
            }
            total = rtnMap.get("system") + rtnMap.get("resource") + rtnMap.get("scheduler");
        }
        if(total != 0) {
            rtnMap.put("total", total);
        }
        return rtnMap;
    }

    /**
     * 当日流数据总量汇总
     * @param index
     * @param type
     * @param dataworkerType
     * @return
     */
    public  Map<String, Long> searchBucketsAggregation(String index, String type, String dataworkerType) {
        Map<String, Long> rtnMap = new HashMap<>();
        long count = 0;
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);

//    	SumAggregationBuilder sAggBuilder = AggregationBuilders.sum("agg").field("count.keyword");

        //搜索条件
        String dateStr = "2018-11-11";
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.must(QueryBuilders.matchQuery("dataworkerType", dataworkerType))
        queryBuilder.must(QueryBuilders.matchQuery("dataworkerType", dataworkerType))
                .filter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("logTime")
                                .gte(dateStr + "000000")
                                .lte(dateStr + "235959")
                                .format("yyyyMMddHHmmss")));
        searchRequestBuilder.setQuery(queryBuilder);
//    	searchRequestBuilder.addAggregation(sAggBuilder);
        SearchResponse response = searchRequestBuilder.execute().actionGet();

        log.info(""+"++++++条件查询结果："+response.toString());

        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit searchHit : hits) {
            Object object = searchHit.getSource().get("count");
            if(object != null && !"".equals(object)) {
                log.info(""+"=============Count:"+object.toString());
                count += Long.parseLong(object.toString());
            }
        }
        log.info(""+"======流数据量：" + count);
        rtnMap.put(dataworkerType + "Total", count);
        log.info(""+"======rtnMap:"+rtnMap.toString());
        return rtnMap;
    }

    /**
     * 读取索引类型表指定列名的平均值
     * @param index
     * @param type
     * @param avgField
     * @return
     */
    public  double readIndexTypeFieldValueWithAvg(String index, String type, String avgField) {
        String avgName = avgField + "Avg";
        AvgAggregationBuilder aggregation = AggregationBuilders.avg(avgName).field(avgField);
        SearchResponse response = client.prepareSearch(index).setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(aggregation).execute().actionGet();
        Avg avg = response.getAggregations().get(avgName);
        return avg.getValue();
    }

    /**
     * 读取索引类型表指定列名的总和
     * @param index
     * @param type
     * @param sumField
     * @return
     */
    public  Map<String, Long> readIndexTypeFieldValueWithSum(String index, String type, String dataworkerType, String sumField) {
        Map<String, Long> rtnMap = new HashMap<>();
        long count = 0;
        // 聚合结果
        String sumName = sumField + "Sum";
        // 搜索条件
        String dateStr = "2018-11-11";
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("dataworkerType", dataworkerType))
                .filter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.rangeQuery("logTime")
                                .gte(dateStr + "000000")
                                .lte(dateStr + "235959")
                                .format("yyyyMMddHHmmss")));
        // 时间前缀匹配（如：20180112）
//        PrefixQueryBuilder preQueryBuild = QueryBuilders.prefixQuery(dataworkerType, dateStr);
// 模糊匹配
//        FuzzyQueryBuilder fuzzyQueryBuild = QueryBuilders.fuzzyQuery(name, value);
// 范围匹配
//        RangeQueryBuilder rangeQueryBuild = QueryBuilders.rangeQuery(name);
// 查询字段不存在及字段值为空 filterBuilder = QueryBuilders.boolQuery().should(new BoolQueryBuilder().mustNot(existsQueryBuilder)) .should(QueryBuilders.termsQuery(field, ""));
//    	ExistsQueryBuilder existsQueryBuilder = QueryBuilders.existsQuery(field);
        // 对某字段求和聚合（sumField字段）
        SumAggregationBuilder aggregation = AggregationBuilders.sum(sumName).field(sumField);
        SearchResponse response = client.prepareSearch(index).setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery())
                .setQuery(queryBuilder)
                .addAggregation(aggregation).execute().actionGet();
        Sum sum = response.getAggregations().get(sumName);
        count = new Double(sum.getValue()).longValue();
        rtnMap.put(dataworkerType + "Total", count);
        log.info(""+"======rtnMap:"+rtnMap.toString());
        return rtnMap;
    }

    /**
     * 按时间统计聚合
     * @param index
     * @param type
     */
    public  void dataHistogramAggregation(String index, String type) {
        try {
            SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);

            DateHistogramAggregationBuilder field = AggregationBuilders.dateHistogram("sales").field("value");
            field.dateHistogramInterval(DateHistogramInterval.MONTH);
//          field.dateHistogramInterval(DateHistogramInterval.days(10))
            field.format("yyyy-MM");

            //强制返回空 buckets,既空的月份也返回
            field.minDocCount(0);

            // Elasticsearch 默认只返回你的数据中最小值和最大值之间的 buckets
            field.extendedBounds(new ExtendedBounds("2018-01", "2018-12"));

            searchRequestBuilder.addAggregation(field);
            searchRequestBuilder.setSize(0);
            SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

            log.info(""+searchResponse.toString());

            Histogram histogram = searchResponse.getAggregations().get("sales");
            for (Histogram.Bucket entry : histogram.getBuckets()) {
//              DateTime key = (DateTime) entry.getKey();
                String keyAsString = entry.getKeyAsString();
                Long count = entry.getDocCount(); // Doc count

                log.info(""+"======="+keyAsString + "，销售" + count + "辆");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述：统计查询
     * @param index 索引名
     * @param type 类型
     * @param constructor 查询构造
     * @param groupBy 统计字段
     */
    /*public Map<Object, Object> statSearch(String index, String type, ESQueryBuilderConstructor constructor, String groupBy) {
        Map<Object, Object> map = new HashedMap();
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
        //排序
        if (StringUtils.isNotEmpty(constructor.getAsc()))
            searchRequestBuilder.addSort(constructor.getAsc(), SortOrder.ASC);
        if (StringUtils.isNotEmpty(constructor.getDesc()))
            searchRequestBuilder.addSort(constructor.getDesc(), SortOrder.DESC);
        //设置查询体
        if (null != constructor) {
            searchRequestBuilder.setQuery(constructor.listBuilders());
        } else {
            searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        }
        int size = constructor.getSize();
        if (size < 0) {
            size = 0;
        }
        if (size > MAX) {
            size = MAX;
        }
        //返回条目数
        searchRequestBuilder.setSize(size);

        searchRequestBuilder.setFrom(constructor.getFrom() < 0 ? 0 : constructor.getFrom());
        SearchResponse sr = searchRequestBuilder.addAggregation(
                AggregationBuilders.terms("agg").field(groupBy)
        ).get();

        Terms stateAgg = sr.getAggregations().get("agg");
        Iterator<Terms.Bucket> iter = (Iterator<Terms.Bucket>) stateAgg.getBuckets().iterator();

        while (iter.hasNext()) {
            Terms.Bucket gradeBucket = iter.next();
            map.put(gradeBucket.getKey(), gradeBucket.getDocCount());
        }

        return map;
    }*/

    /**
     * 功能描述：统计查询
     * @param index       索引名
     * @param type 	  类型
     * @param constructor 查询构造
     * @param agg         自定义计算
     */
    /*public Map<Object, Object> statSearch(String index, String type, ESQueryBuilderConstructor constructor, AggregationBuilder agg) {
        if (agg == null) {
            return null;
        }
        Map<Object, Object> map = new HashedMap();
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
        //排序
        if (StringUtils.isNotEmpty(constructor.getAsc()))
            searchRequestBuilder.addSort(constructor.getAsc(), SortOrder.ASC);
        if (StringUtils.isNotEmpty(constructor.getDesc()))
            searchRequestBuilder.addSort(constructor.getDesc(), SortOrder.DESC);
        //设置查询体
        if (null != constructor) {
            searchRequestBuilder.setQuery(constructor.listBuilders());
        } else {
            searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        }
        int size = constructor.getSize();
        if (size < 0) {
            size = 0;
        }
        if (size > MAX) {
            size = MAX;
        }
        //返回条目数
        searchRequestBuilder.setSize(size);

        searchRequestBuilder.setFrom(constructor.getFrom() < 0 ? 0 : constructor.getFrom());
        SearchResponse sr = searchRequestBuilder.addAggregation(
                agg
        ).get();

        Terms stateAgg = sr.getAggregations().get("agg");
        Iterator<Terms.Bucket> iter = (Iterator<Terms.Bucket>) stateAgg.getBuckets().iterator();

        while (iter.hasNext()) {
            Terms.Bucket gradeBucket = iter.next();
            map.put(gradeBucket.getKey(), gradeBucket.getDocCount());
        }

        return map;
    }*/

    /**
     * 范围查询
     * @throws Exception
     */
    public  void rangeQuery() {

        //查询字段不存在及字段值为空 filterBuilder = QueryBuilders.boolQuery().should(new BoolQueryBuilder().mustNot(existsQueryBuilder)) .should(QueryBuilders.termsQuery(field, ""));
//    	ExistsQueryBuilder existsQueryBuilder = QueryBuilders.existsQuery(field);

        //term精确查询
//        QueryBuilder queryBuilder = QueryBuilders.termQuery("age", 50) ;  //年龄等于50
        //range查询
        QueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age").gt(20); //年龄大于20
        SearchResponse searchResponse = client.prepareSearch("index_test")
                .setTypes("type_test")
                .setQuery(rangeQueryBuilder)     //query
                .setPostFilter(QueryBuilders.rangeQuery("age").from(40).to(50)) // Filter
//              .addSort("age", SortOrder.DESC)
                .setSize(120)   // 不设置的话，默认取10条数据
                .execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        log.info(""+"查到记录数："+hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        if(searchHists.length>0){
            for(SearchHit hit:searchHists){
                String name =  (String) hit.getSource().get("username");
                Integer age = Integer.parseInt(hit.getSource().get("age").toString());
                log.info(""+"姓名：" + name + " 年龄：" + age);
            }
        }
    }

    /**
     * 时间范围查询
     * @param index
     * @param type
     * @param startDate
     * @param endDate
     */
    public  void rangeQuery(String index, String type, String startDate, String endDate) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.must(queryBuilders);
        boolQueryBuilder.filter(QueryBuilders.boolQuery().must(
                QueryBuilders.rangeQuery("time").gte(startDate).lte(endDate).format("yyyyMMddHHmmss")));

        SearchResponse searchResponse = client.prepareSearch(index).setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .addAggregation(AggregationBuilders.terms("value_count").field("value.keyword")).get();
        //Terms Aggregation  名称为terms1_count 字段为field1   下面的也类似
//                .addAggregation(AggregationBuilders.terms("terms2_count").field("field2"));

        log.info(""+"时间范围查询结果： "+searchResponse.toString());

        SearchHit[] hits = searchResponse.getHits().getHits();
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SearchHit searchHit : hits) {
            Map<String, Object> map = searchHit.getSource();
            mapList.add(map);
        }
        log.info(""+mapList.toString());
    }

    /**
     * 功能描述：关闭链接
     */
    public  void close() {
        client.close();
    }

    /**
     * 在Elasticsearch老版本中做数据遍历一般使用Scroll-Scan。Scroll是先做一次初始化搜索把所有符合搜索条件的结果缓存起来生成一个快照，
     * 然后持续地、批量地从快照里拉取数据直到没有数据剩下。而这时对索引数据的插入、删除、更新都不会影响遍历结果，因此scroll 并不适合用来做实时搜索。
     * Scan是搜索类型，告诉Elasticsearch不用对结果集进行排序，只要分片里还有结果可以返回，就返回一批结果。
     * 在5.X版本中SearchType.SCAN已经被去掉了。根据官方文档说明，使用“_doc”做排序可以达到更高性能的Scroll查询效果，
     * 这样可以遍历所有文档而不需要进行排序。
     * @param index
     * @param type
     */
    @SuppressWarnings("deprecation")
    public  void scroll(String index, String type) {
        log.info(""+"scroll()方法开始.....");
        List<JSONObject> lst = new ArrayList<JSONObject>();
        SearchResponse searchResponse = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery())
                .addSort(SortBuilders.fieldSort("_doc"))
                .setSize(30)
                // 这个游标维持多长时间
                .setScroll(TimeValue.timeValueMinutes(8)).execute().actionGet();

        log.info(""+"getScrollId: "+searchResponse.getScrollId());
        log.info(""+"匹配记录数："+searchResponse.getHits().getTotalHits());
        log.info(""+"hits长度："+searchResponse.getHits().hits().length);
        for (SearchHit hit : searchResponse.getHits()) {
            String json = hit.getSourceAsString();
            try {
                JSONObject jsonObject = JSONObject.parseObject(json);
                lst.add(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        log.info(""+"======" + lst.toString());
        log.info(""+"======" + lst.get(0).get("username"));
        // 使用上次的scrollId继续访问
//        croll scroll = new ScrollTest2();
//        do{
//            int num = scroll.scanData(esClient,searchResponse.getScrollId())
//            if(num ==0) break;
//        }while(true);
        log.info(""+"------------------------------END");
    }

    /**
     * 根据属性名称和值来获取单个文档，key应传非text类型字段
     */
    public SearchHit getOneRespByFiledKeyAndValue(String index ,String type ,  String key , Object value ) {
        SearchResponse searchResponse = client.prepareSearch(index)
                .setTypes(type)
                .setVersion(true)
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(key,value)))
                .setSize(1)
                .execute().actionGet();
        if(searchResponse.getHits().totalHits == 1){
            return searchResponse.getHits().getHits()[0];
        }else{
            return null;
        }
    }


    /**
     * 根据属性名称和值来更新单个文档，key应传非text类型字段 ,用version来控制并发安全 ,INTERNAL模式
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean setOneRespByObject(String index,String type , String docId ,Long version , Map productDtoMap ) {
        try {
            // 设置版本号
            UpdateRequest updateRequest = new UpdateRequest().index(index).type(type).id(docId).doc(productDtoMap)
                    .versionType(VersionType.INTERNAL)
                    .version(version);
            UpdateResponse updateResponse = client.update(updateRequest).actionGet();
            if(updateResponse.status().getStatus() == 200){
                log.info("更新成功 [{}]", updateResponse);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            log.error("更新失败[{}]",e.getMessage());
        }
        return false;
    }


}