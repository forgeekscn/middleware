package cn.forgeeks.awesome.feature.mapper.platform;

import cn.forgeeks.awesome.feature.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    Integer getCountOrder();

    Integer insertOrder(@Param("list") List<OrderDto> list);
}
