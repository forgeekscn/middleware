package cn.forgeeks.service.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class ElasticService {

    @Autowired
    LogDao logDao;

    @Autowired
    ElasticUtils elasticUtils;

    public void getInfo(){
        logDao.getInfo();
        try {
            elasticUtils.createIndex("product");
            log.info("exists [{}]",elasticUtils.checkIndexExists("product"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
