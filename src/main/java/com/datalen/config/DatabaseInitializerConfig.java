package com.datalen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * 数据库初始化配置类，确保初始化脚本在应用启动时执行
 */
@Configuration
public class DatabaseInitializerConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        // 添加初始化脚本
        resourceDatabasePopulator.addScript(new ClassPathResource("init.sql"));
        // 设置执行模式，true表示如果脚本执行失败则应用启动失败
        resourceDatabasePopulator.setContinueOnError(false);
        // 设置脚本编码
        resourceDatabasePopulator.setSqlScriptEncoding("UTF-8");

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);

        return dataSourceInitializer;
    }
}