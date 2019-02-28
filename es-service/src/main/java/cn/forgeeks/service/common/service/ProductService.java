package cn.forgeeks.service.common.service;

import cn.forgeeks.service.common.common.IdUtil;
import cn.forgeeks.service.common.common.MyBeanUtils;
import cn.forgeeks.service.common.dto.Consts;
import cn.forgeeks.service.common.dto.ProductDto;
import cn.forgeeks.service.common.dto.ProductQueryDto;
import cn.forgeeks.service.common.dto.ProductResultDto;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductService {

    public Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    TransportClient client;

    @Autowired
    EsCommonService esCommonService;

    @Autowired
    IdUtil idUtil;

    /**
     * 通用scroll分页获取产品
     */
    public Map getCommonScroll(ProductQueryDto productQueryDto , QueryBuilder qb){
        Date begin = new Date();
        List list = new ArrayList<>();
        // 根据商品店铺来聚合统计
        AbstractAggregationBuilder productShopNameAggr = AggregationBuilders.terms("productShopNameAggr").field("productShopName");
        AbstractAggregationBuilder productCategoryAggr = AggregationBuilders.terms("productCategoryAggr").field("productCategory");
        // 现根据日期和得分来排序
        SearchResponse scrollResp = client.prepareSearch(Consts.INDEX_PRODUCT)
                .addSort("createTime",SortOrder.DESC)
                .addAggregation(productShopNameAggr)
                .addAggregation(productCategoryAggr)
                .addSort(new ScoreSortBuilder())
                .setScroll(new TimeValue(60000))
                .setQuery(qb)
                .setSize(productQueryDto.getPageSize())
                .get();
        Integer pageNo = productQueryDto.getPageNo();
        long count = scrollResp.getHits().getTotalHits();
        Integer localPageNo = 1;

        Map map = new HashMap();

        processAggresiveField(scrollResp , map);

        do {
            if( (localPageNo ++ ).equals(pageNo) ){
                for (SearchHit hit : scrollResp.getHits().getHits()) {
                    Map hitMap = hit.getSourceAsMap();
                    ObjectMapper mapper = new ObjectMapper();
                    ProductResultDto productResultDto = mapper.convertValue(hitMap, ProductResultDto.class);
                    productResultDto.setId(hit.getId());
                    productResultDto.setScore(hit.getScore());
                    list.add(productResultDto);
                }
                log.info("查询结果:[{}]" ,list);
                break;
            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId())
                    .setScroll(new TimeValue(60000))
                    .execute().actionGet();
        } while(scrollResp.getHits().getHits().length != 0);

        Date end = new Date();
        map.put("data", list);
        map.put("count",count+" result.");
        map.put("takeTime",(end.getTime()-begin.getTime()  )+" ms.");
        return map;
    }

    /**
     * 处理聚合字段
     */
    private void processAggresiveField(SearchResponse scrollResp ,Map map) {
        // 聚合结果1
        Terms terms = scrollResp.getAggregations().get("productShopNameAggr");
        List<Terms.Bucket> buckets = (List<Terms.Bucket>) terms.getBuckets();
        Map bucketsMap= new HashMap();
        for(Terms.Bucket bucket:buckets){
            bucketsMap.put(bucket.getKey() , bucket.getDocCount());
        }
        log.info("聚合结果 [{}]",bucketsMap);

        // 聚合结果2
        terms = scrollResp.getAggregations().get("productCategoryAggr");
        buckets = (List<Terms.Bucket>) terms.getBuckets();
        Map bucketsMapB= new HashMap();
        for(Terms.Bucket bucket:buckets){
            bucketsMapB.put(bucket.getKey() , bucket.getDocCount());
        }
        log.info("聚合结果 [{}]",bucketsMapB);
        map.put("productShopNameAggr", bucketsMap);
        map.put("productCategoryAggr", bucketsMapB);
    }


    /**
     * 根據 目錄 日期 價格 關鍵詞  的相關性得分排序 查詢
     */
    public Map getProductsByTermAndMatch(ProductQueryDto productQueryDto){
        // 跨字段全文检索
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.should(
                QueryBuilders.multiMatchQuery(productQueryDto.getKeyword())
                        .field("productDesc",1)
                        .field("productName",2)
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
        );
        // 筛选最近多少天的数据
        if( productQueryDto.getDays() !=null ){
            boolQuery.must(QueryBuilders.rangeQuery("createTime")
                     .gte("now-"+productQueryDto.getDays()+"d/d")
                     .lte("now/d")
            );
        }
        // 可出售状态等必要字段筛选
        boolQuery.must(QueryBuilders.termQuery("onSale" , true));
        return getCommonScroll(productQueryDto,boolQuery);
    }


    /**
     * 批量写入数据
     */
    @Transactional(rollbackFor = Exception.class)
    public Map insertProducts(List<ProductDto> productDtoList){
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 0; i < productDtoList.size(); i++) {
            ProductDto productDto = productDtoList.get(i);
            productDto.setCreateTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
            Map prod2 = MyBeanUtils.transBean2Map(productDto);
            bulkRequest.add(
                client.prepareIndex(Consts.INDEX_PRODUCT, Consts.TYPE_PRODUCT)
                        .setSource(prod2)
            );
        }
        bulkRequest.execute().actionGet();

        Map map =new HashMap();
        map.put("code",0);
        map.put("msg","插入成功");
        return map;
    }

    /**
     * 批量写入 num万 数据-測試
     */
    public Map insertProductsForTest(Integer num) {

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 15,
                0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
        AtomicInteger count =new AtomicInteger(0);
        for(long i=1 ; i <= num ; i++){
            threadPool.submit(new WriteThread(count));
        }
        while( threadPool.getActiveCount() > 0 ){
            try {
                Thread.sleep(1000);
                log.info("完成[{}]% 已经插入 [{}] 活跃线程数 [{}]",Math.round( 0.01 * count.get()/num) , count.get(),threadPool.getActiveCount());
            } catch (InterruptedException e) {
                log.error("ERROR:[{}]",e.getMessage());
            }
        }

        Map map =new HashMap();
        map.put("msg","插入成功");
        return map;
    }

    /**
     * 刪除一個
     */
    public Map deleteProducts(ProductDto productDto) {
        esCommonService.deleteData(Consts.INDEX_PRODUCT,Consts.TYPE_PRODUCT ,productDto.getProductId()+"");
        Map map =new HashMap();
        map.put("code",0);
        map.put("msg","刪除成功");
        return map;
    }

    /**
     * 更新一個
     */
    public Map updateProducts(ProductDto productDto) {
        esCommonService.updateData(Consts.INDEX_PRODUCT,Consts.TYPE_PRODUCT ,productDto.getProductId()+"" ,
                JSONObject.toJSONString(productDto));
        Map map =new HashMap();
        map.put("code",0);
        map.put("msg","更新成功");
        return map;
    }

    /**
     * 获取详情
     */
    public Map getProductDetail(long productId) {
        SearchHit searchHit  = esCommonService.getOneRespByFiledKeyAndValue(Consts.INDEX_PRODUCT ,
                Consts.TYPE_PRODUCT , "productId",productId);
        ProductDto productDto = new ProductDto();
        Map<String,Object> searchMap = searchHit.getSourceAsMap();
        productDto = MyBeanUtils.transfer(searchMap,productDto);
        productDto.setProductId(productId);
        // version一定还要取出来 后面乐观锁比对要用
        productDto.setVersion(searchHit.getVersion());
        int saleNumber = (Integer) searchMap.get("saleNumber");
        productDto.setSaleNumber(saleNumber + 0L );
        Map map =new HashMap(2);
        map.put("msg","获取成功");
        map.put("data",productDto);
        log.info("获取商品结果[{}]",productDto);
        return map;
    }

    /**
     * 更新商品,用version来控制并发安全
     */
    @Transactional(rollbackFor = Exception.class)
    public Map setProductDetail(long productId ) {
        Map map = new HashMap(2);
        // 先查询比对
        try {
            SearchHit searchHit = esCommonService.getOneRespByFiledKeyAndValue(Consts.INDEX_PRODUCT,
                    Consts.TYPE_PRODUCT, "productId", productId );
            if (searchHit == null || searchHit.getId() == null) {
                map.put("msg", "操作失败");
                return map;
            }
            String docId = searchHit.getId();
            Long version = searchHit.getVersion();

            // 库存-1
            Integer saleNumber= (Integer) searchHit.getSourceAsMap().get("saleNumber");
            Map productDtoMap =new HashMap();
            productDtoMap.put("saleNumber",saleNumber-1L);

            boolean rs = esCommonService.setOneRespByObject(Consts.INDEX_PRODUCT, Consts.TYPE_PRODUCT,
                    docId ,version, productDtoMap);
            if (rs) {
                map.put("msg", "操作成功");
                // log.info("下订单减库存成功");
            } else {
                map.put("msg", "操作失败");
            }
        }catch (Exception e){
            // log.error("下订单减库存失败[{}]",e.getMessage());
        }
        return map ;
    }

    /**
     * 下订单
     */
    public Map createOrder( long productId ) {
        // ProductDto productDto = (ProductDto) getProductDetail(productId).get("data");
        // productDto.setSaleNumber(productDto.getSaleNumber()-1);
        return setProductDetail(productId);
    }

    /**
     * 测试批量插入数据
     */
    class WriteThread implements Runnable {
        //起始id 1W+
        AtomicInteger count;
        public WriteThread( AtomicInteger count) {
            this.count=count;
        }
        @Override
        public void run() {
            // 批量增加1W条数据
            List<ProductDto> productDtoList = new ArrayList<>();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            for(long i = 0 ; i < 10000 ; i++ ) {
                ProductDto productDto = new ProductDto();
                productDto.setProductId(idUtil.getProductIdFromRedis());
                productDto.setCreateTime(format.format(new Date()));
                productDto.setOnSale(true);
                productDto.setSaleNumber(Math.round(Math.random() * 5000));
                productDto.setProductPrice((double) Math.round(Math.random() * 1000));
                productDto.setProductCategory(Math.random() > 0.5 ? "家装建材>五金工具>智能锁" : "家装建材>五金工具>机械锁");
                productDto.setProductDesc("销售量最高的智能门锁，小米生态链链链智能家居制造商，绿米Aqara智能门锁S2磨砂黑" +
                        "锁安全门锁指纹识别小米智能家居。1.智能家居联动功能；开门自动撤防，开门自动亮灯、非法开锁网关报警等； 2" +
                        ".APP功能：开门记录查询、查看门锁是否反锁、异常开锁多次、非法开锁报警消息推送等； 3.报警提示功能（防撬、低电压、键盘锁定提示）； 4.防猫眼开锁功能； 5.多种独立开锁方式：活体指纹、虚位密码、机械钥匙；");
                productDto.setProductName("绿米Aqara智能门锁S2磨砂黑锁安全门锁指纹识别小米智能家居");
                productDto.setProductMaster("店主");
                productDto.setProductShopName("店铺名称");
                productDto.setProductPic("http://123123.png");
                productDtoList.add(productDto);
                count.incrementAndGet();
            }
            insertProducts(productDtoList);
        }

    }




}
