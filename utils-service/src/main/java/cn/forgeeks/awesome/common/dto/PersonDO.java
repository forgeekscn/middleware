package cn.forgeeks.awesome.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class PersonDO implements Serializable {

    private Integer id;

    private String name;

    private Integer age;

}