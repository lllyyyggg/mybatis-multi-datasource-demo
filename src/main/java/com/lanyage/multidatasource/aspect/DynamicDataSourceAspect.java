package com.lanyage.multidatasource.aspect;

import com.lanyage.multidatasource.dynamicds.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 动态的数据源的切换一般用来实现数据库的读写分离的。
 * <p>
 * 我这里使用其根据不同的业务调用不同的数据库。
 */
@Aspect
@Component
//@Order(0) //在事务执行之前执行
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Pointcut("execution(public * com.lanyage.multidatasource.dao.*.*(..))")
    public void daoAspect() {
    }

    private static final String DOG_SOURCE = "DogMapper";

    // 在dao方法执行之前拦截
    @Before("daoAspect()")
    public void beforeUserSwitchDataSource(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature());
        boolean isFromDogMapper = isFrom(DOG_SOURCE, joinPoint.getSignature().toString());
        if (isFromDogMapper) {
            DynamicDataSourceContextHolder.useSlaveDataSource();
            logger.info(">>>>>>Switch DataSource to [{}] in method [{}]",
                    DynamicDataSourceContextHolder.getDataSourceKey(),
                    joinPoint.getSignature().getName()
            );
        }
    }

    // 在dao方法执行之后拦截
    @After("daoAspect()")
    public void restoreDataSource(JoinPoint joinPoint) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
        logger.info(">>>>>>Restore DataSource to [{}] in method [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(),
                joinPoint.getSignature().getName()
        );
    }

    private boolean isFrom(String dogMapper, String name) {
        return name.contains(dogMapper);
    }
}