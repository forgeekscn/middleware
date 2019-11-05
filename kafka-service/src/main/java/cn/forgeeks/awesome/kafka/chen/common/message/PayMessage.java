package cn.forgeeks.awesome.kafka.chen.common.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PayMessage {
    private String orderCode;
    private Float fee;
    private Long sendTime;
}
