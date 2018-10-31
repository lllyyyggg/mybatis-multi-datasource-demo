package com.lanyage.multidatasource.dao;

import com.lanyage.multidatasource.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select(value = "select * from sys_user")
    List<User> loadAllUsers();
}
