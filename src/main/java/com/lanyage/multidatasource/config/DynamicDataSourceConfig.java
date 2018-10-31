package com.lanyage.multidatasource.config;

import com.lanyage.multidatasource.constant.DataSourceKey;
import com.lanyage.multidatasource.dynamicds.DynamicDataSourceContextHolder;
import com.lanyage.multidatasource.dynamicds.DynamicRoutingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfig {

    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("primaryDataSource") DataSource primaryDataSource,
                                        @Qualifier("secondaryDataSource")DataSource secondaryDataSource) {

        //DynamicRoutingDataSource 该DataSource是一个特殊的DataSource,用来实现数据源的切换
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();

        //将全部的数据源通过键值对的形势存储 { PRIMARY : primaryDataSource@124daf13 }
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(DataSourceKey.PRIMARY.name(), primaryDataSource);
        dataSourceMap.put(DataSourceKey.SECONDARY.name(), secondaryDataSource);

        //默认的数据源
        dynamicRoutingDataSource.setDefaultTargetDataSource(primaryDataSource);
        //所有的数据源，key为枚举类,如:
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        //PRIMARY, SECONDARY
        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());

        DynamicDataSourceContextHolder.slaveDataSourceKeys.addAll(dataSourceMap.keySet());
        DynamicDataSourceContextHolder.slaveDataSourceKeys.remove(DataSourceKey.PRIMARY.name());

        return dynamicRoutingDataSource;
    }

    @Bean(name = "dynamicJdbcTemplate")
    public JdbcTemplate dynamicJdbcTemplate(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new JdbcTemplate(dynamicDataSource);
    }

    @Bean(name = "dynamicDataSourceTransactionManager")
    public DataSourceTransactionManager dynamicDataSourceTransactionManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}
