package cn.forgeeks.service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultDto {
    private Integer code;
    private String msg;
    private Object data;
}
