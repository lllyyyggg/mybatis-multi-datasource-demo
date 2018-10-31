package com.lanyage.multidatasource.dynamicds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(DynamicRoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        Object key;
        logger.info("Current DataSource is [{}]", key = DynamicDataSourceContextHolder.getDataSourceKey());
        return key;
    }
}
