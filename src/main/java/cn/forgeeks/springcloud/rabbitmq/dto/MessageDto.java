package cn.forgeeks.springcloud.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Message dto.
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class MessageDto implements Serializable {

    private String id;

    private String title;

    private String content;

    private String pusher;

    private String consumer;

    private Date createTime;

    private Map remark;

    public MessageDto(String id , String title){
        this.id = id;
        this.title = title;
    }
}
