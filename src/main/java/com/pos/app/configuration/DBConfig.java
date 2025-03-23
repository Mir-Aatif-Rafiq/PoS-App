package com.pos.app.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
@Configuration
public class DBConfig {
    public static final String PACKAGE_POJO = "com.pos.app.pojo";
    private static final String VALIDATION_QUERY = "SELECT 1";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String HIBERNATE_HBM2DDL = "hibernate.hbm2ddl.auto";

    @Value("${jdbc.driverClassName}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;
    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddl;


    @Bean
    public DataSource getDataSource() {
        BasicDataSource bean = new BasicDataSource();
        bean.setDriverClassName(jdbcDriver);
        bean.setUrl(jdbcUrl);
        bean.setUsername(jdbcUsername);
        bean.setPassword(jdbcPassword);
        bean.setInitialSize(2);
        bean.setDefaultAutoCommit(false);
        bean.setMinIdle(2);
        bean.setValidationQuery(VALIDATION_QUERY);
        bean.setTestWhileIdle(true);
        bean.setTimeBetweenEvictionRunsMillis(10 * 60 * 100);
        return bean;
    }

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan(PACKAGE_POJO);
        HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(jpaAdapter);
        Properties jpaProperties = new Properties();
        jpaProperties.put(HIBERNATE_DIALECT, hibernateDialect);
        jpaProperties.put(HIBERNATE_SHOW_SQL, hibernateShowSql);
        jpaProperties.put(HIBERNATE_HBM2DDL, hibernateHbm2ddl);
        jpaProperties.put(HIBERNATE_HBM2DDL, hibernateHbm2ddl);
        bean.setJpaProperties(jpaProperties);
        return bean;
    }

    @Bean(name = "transactionManager")
    @Autowired
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
        JpaTransactionManager bean = new JpaTransactionManager();
        bean.setEntityManagerFactory(emf.getObject());
        return bean;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
