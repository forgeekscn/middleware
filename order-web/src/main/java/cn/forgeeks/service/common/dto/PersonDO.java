package cn.forgeeks.service.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonDO implements Serializable {

    private Integer id;

    private String name;

    private Integer age;

}