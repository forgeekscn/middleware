package cn.forgeeks.awesome.order.configure;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

//表示这个类为一个配置类
@Configuration
@Slf4j
// 配置mybatis的接口类放的地方
@MapperScan(basePackages = "cn.forgeeks.awesome.order.mapper.platform",
        sqlSessionFactoryRef = "dbgeekplatformSqlSessionFactory")
public class DataSourceDbGeekPlatformConfig {


    // 将这个对象放入Spring容器中
    @Bean(name = "dbgeekplatformDataSource")
    // 表示这个数据源是默认数据源
    @ConfigurationProperties(prefix = "spring.datasource.dbgeekplatform")
    public DataSource getDbgeekplatformDataSource() {
        return DataSourceBuilder.create().build();
    }



    @Bean(name = "dbgeekplatformSqlSessionFactory")
    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    public SqlSessionFactory dbgeekplatformSqlSessionFactory(@Qualifier("dbgeekplatformDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath*:mapper/platform/*.xml"));
        return bean.getObject();
    }



    @Bean("dbgeekplatformSqlSessionTemplate")
    public SqlSessionTemplate dbgeekplatformSqlSessionTemplate(
            @Qualifier("dbgeekplatformSqlSessionFactory") SqlSessionFactory sessionfactory) {
        log.info("### dbgeekplatformSqlSessionFactory 数据源配置陈功");
        return new SqlSessionTemplate(sessionfactory);
    }
}