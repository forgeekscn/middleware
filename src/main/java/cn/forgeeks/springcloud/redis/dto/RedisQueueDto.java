package cn.forgeeks.springcloud.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class RedisQueueDto implements Serializable {

    private String index;

    private String type;

    private String doc;

    private String key;

}
