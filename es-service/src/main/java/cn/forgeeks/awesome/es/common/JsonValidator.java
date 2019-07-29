package cn.forgeeks.awesome.es.common;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonValidator {
    public static boolean validate(String jsonString) {
        boolean result = false;
        try {
            Object obj = JSON.parse(jsonString);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        log.info("json参数 [{}]", jsonString);
        return result;
    }
}
