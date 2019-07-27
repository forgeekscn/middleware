package cn.forgeeks.service.common.controller;

import cn.forgeeks.service.common.service.OrderService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/datasource")
@Slf4j
public class DataResourceController {

    @Autowired
    OrderService orderService;

    //@ApiOperation(nickname = "测试数据源" ,notes = "测试数据源", value = "测试数据源")
//    @ResponseBody
    @RequestMapping(value = "/test" , method = RequestMethod.POST)
    public JSONObject test(@RequestBody String obj){
        JSONObject rs = new JSONObject();
        rs.put("getOrderLogCount",orderService.getOrderLogCount());
        rs.put("getUserCount",orderService.getUserCount());
        log.info("### test {}",rs);
        return rs;
    }

}
