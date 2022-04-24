package com.digdes;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.ResourceBundle;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.digdes"})
@EnableJpaRepositories(basePackages = "com.digdes.repositories")
@PropertySource("classpath:/db.properties")
public class HibernateConfig {
//todo: spring boot hibernate config
//todo: spring boot liquibase
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        ResourceBundle rb = ResourceBundle.getBundle("db");
        String driver = rb.getString("postgres_db_driver");
        String url = rb.getString("postgres_db_url");
        String user = rb.getString("postgres_db_username");
        String password = rb.getString("postgres_db_password");
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public Properties jpaProperties(){
        Properties hibernateProperties = new Properties();
        hibernateProperties
                .setProperty("hibernate.hbm2ddl.auto", "validate");
        hibernateProperties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        return hibernateProperties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.digdes.models");
        factory.setDataSource(dataSource());
        factory.setJpaProperties(jpaProperties());
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
