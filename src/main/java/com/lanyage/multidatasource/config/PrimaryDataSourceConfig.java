package com.lanyage.multidatasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class PrimaryDataSourceConfig {

    @Value("${primary.spring.datasource.jdbc-url}")
    private String url;
    @Value("${primary.spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${primary.spring.datasource.username}")
    private String username;
    @Value("${primary.spring.datasource.password}")
    private String password;

    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "primary.spring.datasource")  //将属性文件封装到实体DataSource
    public DataSource primaryDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }

    /*----------------spring jdbc module-----------------*/
    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
        return new JdbcTemplate(primaryDataSource);
    }

    @Bean(name = "primaryDataSourceTransactionManager")
    public DataSourceTransactionManager primaryDataSourceTransactionManager(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
        return new DataSourceTransactionManager(primaryDataSource);
    }
}
