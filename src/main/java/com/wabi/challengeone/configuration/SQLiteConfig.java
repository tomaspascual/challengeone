package com.wabi.challengeone.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


public class SQLiteConfig {

    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.driverClassName(env.getProperty("driverClassName"));
        dataSource.url(env.getProperty("url"));
        dataSource.username(env.getProperty("user"));
        dataSource.password(env.getProperty("password"));
        return dataSource.build();
    }

}
