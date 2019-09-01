package cn.forgeeks.awesome.mq.common.consumer;

import cn.forgeeks.awesome.common.dto.MessageDto;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 专门监听秒杀消费过程
 */
@Slf4j
//@Component("rabbitOrderListener")
public  abstract class AbstarctCommonConsumer implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        Long tag = message.getMessageProperties().getDeliveryTag();
        byte[] body = message.getBody();
        MessageDto msg = byteArrayToObject(body);
        try {
            log.info("消费者监听器接收到一条消息  =>  {} " , msg);
            processMessage(message , channel);
            channel.basicAck(tag, true);
        } catch (Exception e) {
            channel.basicReject(tag, false);
            log.error("秒杀失败:[{}]", e);
        }

    }

    public  abstract void processMessage(Message message, Channel channel) ;

    /**
     * Byte数组转对象
     */
    public MessageDto byteArrayToObject(byte[] bytes) {
        MessageDto obj = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = (MessageDto) objectInputStream.readObject();
        } catch (Exception e) {
            log.error("byteArrayToObject failed, " + e);
        } finally {
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    log.error("close byteArrayInputStream failed, " + e);
                }
            }
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    log.error("close objectInputStream failed, " + e);
                }
            }
        }
        return obj;
    }


}
