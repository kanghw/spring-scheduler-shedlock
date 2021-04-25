package com.example.springschedulershedlock.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdbcConfig {

    @Bean(name = "shedlockDataSource")
    public DataSource dormantMasterDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        config.setJdbcUrl("192.168.1.219:13306");
        config.setJdbcUrl("jdbc:mysql://192.168.1.219:13306/shedlock?serverTimezone=Asia/Seoul&useUnicode=true&characterEncoding=utf8&useSSL=false");
        config.setUsername("test");
        config.setPassword("testPass");
        return new HikariDataSource(config);
    }
}
