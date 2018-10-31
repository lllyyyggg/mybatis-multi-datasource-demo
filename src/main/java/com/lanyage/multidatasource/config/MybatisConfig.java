package com.lanyage.multidatasource.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MybatisConfig {
    private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        logger.info(">>>>>>mapper scanner configuration.>>>>>>");
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        //mapperScannerConfigurer.setSqlSessionTemplateBeanName("secondarySqlSessionTemplate");
        //mapperScannerConfigurer.setSqlSessionTemplateBeanName("primarySqlSessionTemplate");
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("dynamicSqlSessionTemplate");
        mapperScannerConfigurer.setBasePackage("com.lanyage.multidatasource.dao");
        return mapperScannerConfigurer;
    }

    /*---------------mybatis module----------------*/

    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSources) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(primaryDataSources);
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        //bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "primarySqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory primarySqlSessionFactory) {
        return new SqlSessionTemplate(primarySqlSessionFactory);
    }

    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("secondaryDataSource") DataSource secondaryDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(secondaryDataSource);
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        //bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "secondarySqlSessionTemplate")
    public SqlSessionTemplate secondarySqlSessionTemplate(@Qualifier("secondarySqlSessionFactory") SqlSessionFactory secondarySqlSessionFactory) {
        return new SqlSessionTemplate(secondarySqlSessionFactory);
    }


    @Bean(name = "dynamicSqlSessionFactory")
    public SqlSessionFactory dynamicSqlSessionFactory(@Qualifier("dynamicDataSource")DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        return bean.getObject();
    }

    @Bean(name = "dynamicSqlSessionTemplate")
    public SqlSessionTemplate dynamicSqlSessionTemplate(@Qualifier("dynamicSqlSessionFactory") SqlSessionFactory dynamicSqlSessionFactory) {
        return new SqlSessionTemplate(dynamicSqlSessionFactory);
    }
}
