package com.soccertennisgame.soccertennis.CasinoService;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.soccertennisgame.soccertennis.SqlRepository",
        entityManagerFactoryRef = "mssqlEntityManagerFactory",
        transactionManagerRef = "mssqlTransactionManager"
)
public class SQLServerConfig {
    @Primary
    @Bean(name = "mssqlDataSource")
    @ConfigurationProperties(prefix = "spring.mssql.datasource")
    public DataSource mssqlDataSource() {
        return new HikariDataSource();
    }

    @Primary
    @Bean(name = "mssqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mssqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mssqlDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("com.soccertennisgame.soccertennis.SqlModel")
                .persistenceUnit("mssql")
                .build();
    }

    @Primary
    @Bean(name = "mssqlTransactionManager")
    public PlatformTransactionManager mssqlTransactionManager(
            @Qualifier("mssqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}