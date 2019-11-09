package cn.forgeeks.awesome.order.controller;

import cn.forgeeks.awesome.common.dto.MessageSendDto;
import cn.forgeeks.awesome.common.dto.ResultDto;
import cn.forgeeks.awesome.es.common.ElasticService;
import cn.forgeeks.awesome.mq.common.sender.*;
import cn.forgeeks.awesome.order.service.OrderService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 *  队列测试类
 */
@RestController
@Slf4j
@RequestMapping(value = "/rabbit")
public class LogSpyController {

    @Autowired(required = false)
    RabbitLogSender rabbitSender;

    @Autowired(required = false)
    RabbitMailSender rabbitMailSender;

    @Autowired(required = false)
    RabbitOrderSender rabbitOrderSender;

    @Autowired(required = false)
    RabbitDirectSender rabbitDirectSender;

    @Autowired(required = false)
    RabbitFanoutSender rabbitFanoutSender;

    @Autowired(required = false)
    OrderService orderService;

    @Qualifier("ElasticService")
    @Autowired(required = false)
    ElasticService elasticService;

    @ApiOperation(value ="es测试", notes = "es测试")
    @PostMapping(value = "/test22")
    public ResultDto test22(@RequestBody MessageSendDto message){
        elasticService.getInfo();
        return new ResultDto(200,"Success.");
    }




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
//        GetResponse response = channel.basicGet(MqConsts.QUEUE_MYTH_ORDER_ALL,true);
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
