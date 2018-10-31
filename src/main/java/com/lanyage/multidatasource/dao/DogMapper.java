package com.lanyage.multidatasource.dao;

import com.lanyage.multidatasource.bean.Dog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DogMapper {
    @Select(value = "select * from dog")
    List<Dog> loadAllDogs();
}
