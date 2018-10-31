spring+mybatis的多数据源配置方式。

使用了`DynamicRoutingDataSource extends AbstractRoutingDataSource`来实现数据源的动态切换。该类有一个抽象方法`determineCurrentLookupKey`用来返回数据源的key。

使用到了`@Value("${xxx.xxx}")`来获取某个属性值

使用了`@ConfigurationProperties(prefix = "primary.spring.datasource")`来将属性进行封装进实体，一般用在类上或`@Bean`修饰的方法上。


还用到了Spring的AutoConfig属性。在META-INF下创建spring.factories文件。内容如下：

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.lanyage.multidatasource.autoconfig.UserAutoConfig
...
```
被配置的类会在被集成到别的spring项目后进行自动实例化。



 