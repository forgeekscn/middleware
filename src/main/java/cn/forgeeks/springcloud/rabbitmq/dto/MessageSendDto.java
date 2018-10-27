package cn.forgeeks.springcloud.rabbitmq.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageSendDto implements Serializable {
    MessageDto message;
    String exchange;
    String bindingKey;
}
