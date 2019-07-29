package cn.forgeeks.awesome.order.mapper.log;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderLogMapper {
    Integer getOrderLogCount(@Param("date") String date);
}
