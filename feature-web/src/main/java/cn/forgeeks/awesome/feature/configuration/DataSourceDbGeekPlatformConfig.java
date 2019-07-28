package cn.forgeeks.awesome.feature.configuration;

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

@Configuration
@Slf4j
@MapperScan(basePackages = "cn.forgeeks.awesome.feature.mapper.platform",
        sqlSessionFactoryRef = "dbgeekplatformSqlSessionFactory")
public class DataSourceDbGeekPlatformConfig {
    @Bean(name = "dbgeekplatformDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.dbgeekplatform")
    public DataSource getDbgeekplatformDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "dbgeekplatformSqlSessionFactory")
    public SqlSessionFactory dbgeekplatformSqlSessionFactory(@Qualifier("dbgeekplatformDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations( new PathMatchingResourcePatternResolver()
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