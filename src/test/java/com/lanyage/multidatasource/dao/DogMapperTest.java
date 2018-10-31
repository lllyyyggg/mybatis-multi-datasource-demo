package com.lanyage.multidatasource.dao;

import com.lanyage.multidatasource.bean.Dog;
import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DogMapperTest {

    @Autowired
    private DogMapper dogMapper;

    @Test
    public void loadAllDogs() {
        List<Dog> dogs = dogMapper.loadAllDogs();
        System.out.println(dogs);
    }
}