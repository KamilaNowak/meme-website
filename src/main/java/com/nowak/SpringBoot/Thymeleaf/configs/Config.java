//package com.nowak.SpringBoot.Thymeleaf.configs;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableJpaRepositories
//@ComponentScan(basePackages = "com.nowak.SpringBoot.Thymeleaf")
//public class Config {
//
//    @Bean
//    @ConfigurationProperties(prefix = "app.database")
//        public DataSource appDataSource() {
//            return DataSourceBuilder.create().build();
//        }
//
//        @Bean
//        @ConfigurationProperties(prefix="spring.data.jpa.entity")
//        public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource appDataSource) {
//            return builder
//                    .dataSource(appDataSource)
//                    .build();
//        }
//}
