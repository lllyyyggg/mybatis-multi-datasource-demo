package com.lanyage.multidatasource.template;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcTemplateTest {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate secondaryJdbcTemplate;

    @Test
    public void testSelectAll() {
        List<Map<String, Object>> users = primaryJdbcTemplate.queryForList("select * from sys_user");
        System.out.println(primaryJdbcTemplate.getDataSource().getClass());
        System.out.println(users);
        List<Map<String, Object>> dogs = secondaryJdbcTemplate.queryForList("select * from dog");
        System.out.println(secondaryJdbcTemplate.getDataSource().getClass());
        System.out.println(dogs);
    }
}
