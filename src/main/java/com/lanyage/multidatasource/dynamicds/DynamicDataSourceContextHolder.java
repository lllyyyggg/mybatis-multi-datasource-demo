package com.lanyage.multidatasource.dynamicds;

import com.lanyage.multidatasource.constant.DataSourceKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DynamicDataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    private static Lock lock = new ReentrantLock();

    private static int counter = 0;

    private static final ThreadLocal<Object> CONTEXT_HOLDER = ThreadLocal.withInitial(() -> DataSourceKey.PRIMARY.name());

    public static List<Object> dataSourceKeys = new ArrayList<>();

    public static List<Object> slaveDataSourceKeys = new ArrayList<>();

    public static void setDataSourceKeys(String key) {
        CONTEXT_HOLDER.set(key);
    }

    public static void useMasterDataSource() {
        CONTEXT_HOLDER.set(DataSourceKey.PRIMARY.name());
    }

    public static void useSlaveDataSource() {
        lock.lock();
        try {
            int slaveDataSourceKeyIndex = counter++ % slaveDataSourceKeys.size();
            CONTEXT_HOLDER.set(String.valueOf(slaveDataSourceKeys.get(slaveDataSourceKeyIndex)));
        } catch (Exception e) {
            logger.error("Switch slave dataSource failed. error message is {}", e.getMessage());
            useMasterDataSource();
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static String getDataSourceKey() {
        return (String) CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();    //remove the non-default key, keep the default key.
    }

    public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);
    }
}
