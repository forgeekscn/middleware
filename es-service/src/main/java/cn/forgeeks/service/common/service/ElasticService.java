package cn.forgeeks.service.common.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service("ElasticService")
@Slf4j
public class ElasticService {

    @Qualifier("ElasticUtils")
    @Autowired
    ElasticUtils elasticUtils;

    public void getInfo() {
        try {
            elasticUtils.createIndex("product");
            log.info("exists [{}]", elasticUtils.checkIndexExists("product"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createIndex() {
        try {
            elasticUtils.createIndex("order", "xiaomi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDoc() {
        Map map = new HashMap();
        int num = new Random().nextInt(99999);
        map.put("orderId", "" + num);
        map.put("orderName", "工单" + num);
        map.put("customerName", "何超");
        map.put("level", "info");
        map.put("message", "订单备注" + num);
        map.put("createTime", DateUtils.formatDate(Calendar.getInstance().getTime(), "YYYY-MM-dd'T'HH:mm:ss.SSSZ"));

        String str = JSON.toJSONString(map);
        try {
            elasticUtils.addDocByJson("order", "xiaomi",
                    new Random().nextInt(100) + "", str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queryAllByConditions() {
        try {
            List<Map<String, Object>> list = elasticUtils.queryAllByConditions("info", "", null, null);
            log.info("查询结果[{}]", list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void queryPageByConditions() {
        try {
            Page<Map<String, Object>> list = elasticUtils.queryPageByConditions("info", "工单", null, null, 1, 10);
            list.getResults().forEach(item -> {
                log.info("查询结果[{}]", item);
            });
            List<Map<String, Object>> list2 = elasticUtils.queryAllByConditions("info", "工单", null, null);
            list2.forEach(item -> {
                log.info("查询结果[{}]", item);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void clearIndex() {
        try {
            elasticUtils.deleteIndex("order");
            // elasticUtils.createIndex("order", "xiaomi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public JSON matchQueryByIndex(String index, JSON queryJson, long pageNo, long pageSize) {
        if (!elasticUtils.checkIndexExists(index)) {
            log.info("索引不存在新建一个");
            try {
                elasticUtils.createIndex(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
//        return elasticUtils.matchQueryByIndex(index, queryJson, pageNo, pageSize);
    }


}
