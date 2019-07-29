package cn.forgeeks.awesome.order.mapper.platform;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper{

    Integer getUserCount(@Param("type") String type);



}
