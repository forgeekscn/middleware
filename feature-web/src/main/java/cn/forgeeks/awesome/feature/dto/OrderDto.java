package cn.forgeeks.awesome.feature.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderDto implements Serializable {
    private Long id;
    private String orderName;
    private String orderNo;
    private Date createTime;
    private Integer qps;
}
