package cn.forgeeks.awesome.es.common;

import lombok.Data;
import java.util.List;

@Data
public class Page<T> {
    private Integer pageNum;
    private Integer pageSize;
    private int totalRecord;
    private  List<T> results;
}
