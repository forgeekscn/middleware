package cn.forgeeks.service.common.mapper;

import cn.forgeeks.service.common.dto.PersonDO;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface PersonMapper {

    /**
     * 添加操作，返回新增元素的 ID
     */
    @Insert("insert into person(name,age) values(#{name},#{age})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(PersonDO personDO);

    /**
     * 更新操作
     */
    @Update("update person set name=#{name},age=#{age} where id=#{id}")
    Long update(PersonDO personDO);

    /**
     * 删除操作
     */
    @Delete("delete from person where id=#{id}")
    Long delete(@Param("id") Long id);

    /**
     * 查询所有
     */
    @Select("select id,name,age from person")
    List<PersonDO> selectAll();

    /**
     * 根据主键查询单个
     */
    @Select("select id,name,age from person where id=#{id}")
    PersonDO selectById(@Param("id") Long id);
}