package cn.forgeeks.springcloud.mybatis.controller;

import cn.forgeeks.springcloud.mybatis.entity.PersonDO;
import cn.forgeeks.springcloud.mybatis.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/mybatis")
@EnableTransactionManagement
@RestController
public class PersonController {

    @Autowired
    private PersonMapper personMapper;

    @RequestMapping("/save")
    public Integer save() {
        PersonDO personDO = new PersonDO();
        personDO.setName("张三");
        personDO.setAge(18);
        personMapper.insert(personDO);
        return personDO.getId();
    }

    @RequestMapping("/update")
    public Long update() {
        PersonDO personDO = new PersonDO();
        personDO.setId(2);
        personDO.setName("旺旺");
        personDO.setAge(12);
        return personMapper.update(personDO);
    }

    @RequestMapping("/delete")
    public Long delete() {
        return personMapper.delete(11L);
    }

    @RequestMapping("/selectById")
    public PersonDO selectById() {
        return personMapper.selectById(2L);
    }

    @RequestMapping("/selectAll")
    public List<PersonDO> selectAll() {
        return personMapper.selectAll();
    }

    @RequestMapping("/transaction")
    @Transactional
    public Boolean transaction() {
        delete();

        int i = 3 / 0;

        save();

        return true;
    }

}