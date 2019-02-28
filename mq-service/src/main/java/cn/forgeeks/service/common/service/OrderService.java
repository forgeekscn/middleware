package cn.forgeeks.service.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    Logger log = LoggerFactory.getLogger(OrderService.class);

    /**
     * 秒杀业务接口
     */
    @Transactional(rollbackFor = Exception.class)
    public Object createOrderByphone(String phone ) throws Exception {
        Thread.sleep(1000);
        return "恭喜秒杀成功!订单号:112312323,请在15分钟内付款!";
    }

}
