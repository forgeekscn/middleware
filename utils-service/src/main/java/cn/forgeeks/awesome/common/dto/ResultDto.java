package cn.forgeeks.awesome.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultDto {
    private Integer code;
    private String msg;
    private Object data;

    public ResultDto(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
