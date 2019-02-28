package cn.forgeeks.service.common.controller;

import cn.forgeeks.service.common.dto.ResultDto;
import cn.forgeeks.service.common.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {


    @Autowired
    OrderService orderService;

    @GetMapping(name = "/order/get")
    public ResultDto getOrder(){
        orderService.test();
        return new ResultDto(0,"success","");
    }

}
