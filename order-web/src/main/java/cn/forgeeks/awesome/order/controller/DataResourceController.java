package cn.forgeeks.awesome.order.controller;

import cn.forgeeks.awesome.common.dto.MessageSendDto;
import cn.forgeeks.awesome.common.dto.ResultDto;
import cn.forgeeks.awesome.es.common.ElasticService;
import cn.forgeeks.awesome.order.service.OrderService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/datasource")
@Slf4j
public class DataResourceController {

    @Autowired(required = false)
    OrderService orderService;

    @Autowired(required = false)
    ElasticService elasticService;



    @ApiOperation(nickname = "测试数据源" ,notes = "测试数据源", value = "测试数据源")
    @ResponseBody
    @RequestMapping(value = "/test" , method = RequestMethod.POST)
    public JSONObject test(@RequestBody String obj){
        JSONObject rs = new JSONObject();
        rs.put("getOrderLogCount",orderService.getOrderLogCount());
        rs.put("getUserCount",orderService.getUserCount());
        log.info("### test {}",rs);
        return rs;
    }

    @ApiOperation(nickname = "测试es" ,notes = "测试es", value = "测试es")
    @ResponseBody
    @PostMapping(value = "/test2" )
    public ResultDto testEs(@RequestBody MessageSendDto message){
        String str="{\n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  }\n" +
                "}";
//        elasticService.matchQueryByIndex(".kibana_1",JSONObject.parseObject(str),1,10);
        elasticService.getInfo();
        return new ResultDto(200,"Success.");
    }


}
