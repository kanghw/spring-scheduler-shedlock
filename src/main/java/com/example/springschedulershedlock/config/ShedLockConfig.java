package com.example.springschedulershedlock.config;

import java.util.Random;
import javax.sql.DataSource;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "15m")
public class ShedLockConfig {

    @Bean
    public LockProvider lockProvider(@Qualifier("shedlockDataSource") DataSource dataSource) {
        return new JdbcTemplateLockProvider(
            JdbcTemplateLockProvider.Configuration.builder()
                .withJdbcTemplate(new JdbcTemplate(dataSource))
                // locked_by 서버 별 설정 확인 용도. -> hostname 등의 값이 다를 경우 활용 가능.
                .withLockedByValue("shedlockTestServer-" + new Random().nextInt())
                .usingDbTime()
                .build()
        );
    }

    /**
     * database 에 생성해야 하는 기본 테이블.
     * CREATE TABLE shedlock
     * (
     *     name       VARCHAR(64)  NOT NULL,
     *     lock_until TIMESTAMP(3) NOT NULL,
     *     locked_at  TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
     *     locked_by  VARCHAR(255) NOT NULL,
     *     PRIMARY KEY (name)
     * );
     */
}
