package cn.forgeeks.service.common.controller;

import cn.forgeeks.service.common.common.*;
import cn.forgeeks.service.common.dto.MessageSendDto;
import cn.forgeeks.service.common.dto.ResultDto;
import cn.forgeeks.service.common.service.OrderService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

/**
 *  队列测试类
 */
@RestController
@Slf4j
@RequestMapping(value = "/rabbit")
public class LogSpyController {

    @Autowired
    RabbitLogSender rabbitSender;

    @Autowired
    RabbitMailSender rabbitMailSender;

    @Autowired
    RabbitOrderSender rabbitOrderSender;

    @Autowired
    RabbitDirectSender rabbitDirectSender;

    @Autowired
    RabbitFanoutSender rabbitFanoutSender;


    @Autowired
    OrderService orderService;


    /**
     * 模拟生产特定类型消息并消费
     */
    @PostMapping(value = "/sendLogMsg")
    public ResultDto sendMsgWithCustomParams(@RequestBody MessageSendDto message){
        rabbitSender.sendWithCustomParams(message.getMessage(),message.getExchange() , message.getBindingKey());
        return new ResultDto(200,"Success.");
    }

    /**
     * 异步发送邮件
     */
    @RequestMapping(value = "/sendMail")
    public ResultDto sendMailAsyn(@RequestBody MessageSendDto mail){
        rabbitMailSender.sendMail(mail.getMessage());
        return new ResultDto(200,"Success.");
    }

    /**
     *  模拟秒杀场景消峰减压
     */
    @RequestMapping(value = "/createOrder" ,method = RequestMethod.POST)
//    @ApiImplicitParam(name = "MessageSendDto", value = "测试消息体MessageSendDto", required = true,
//            dataTypeClass = cn.forgeeks.service.common.dto.MessageSendDto.class,dataType = "MessageSendDto")
    public ResultDto createOrder(/*@ApiParam(required=true, name="param", value="测试消息体")*/
                                     @RequestBody  MessageSendDto  param){

        JSONObject rs = new JSONObject();
        rs.put("getOrderLogCount",orderService.getOrderLogCount());
        rs.put("getUserCount",orderService.getUserCount());
        log.info("### test {}",rs);

        rabbitOrderSender.sendOrderMsg( param.getMessage() ) ;
        return new ResultDto(200,"Success.");
    }
//
//    @Autowired
//    RabbitConfig rabbitConfig;
//    /**
//     *  模拟秒杀场景消峰减压
//     */
//    @RequestMapping(value = "/createOrderTemp" )
//    public ResultDto createOrderForNewMsg() throws IOException {
//        Connection conn  =  rabbitConfig.connectionFactory().createConnection();
//        Channel channel = conn.createChannel(true) ;
//        GetResponse response = channel.basicGet(Consts.QUEUE_MYTH_ORDER_ALL,true);
//        log.info("pull消息结果[{}]",new String(response.getBody()));
//        channel.basicAck(response.getEnvelope().getDeliveryTag(),false);
//        try {
//            channel.close();
//            conn.close();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//        return new ResultDto(200,"Success.");
//    }


    /**
     *  测试Direct模式的消息分发
     */
    @RequestMapping(value = "/direct")
    public ResultDto directTest(@RequestBody MessageSendDto param){
        rabbitDirectSender.send(param.getMessage());
        return new ResultDto(200,"Success.");
    }

    /**
     *  测试广播模式消息分发
     */
    @RequestMapping(value = "/fanout")
    public ResultDto fanoutTest(@RequestBody MessageSendDto param){
        rabbitFanoutSender.send(param.getMessage());
        return new ResultDto(200,"Success.");
    }
}
