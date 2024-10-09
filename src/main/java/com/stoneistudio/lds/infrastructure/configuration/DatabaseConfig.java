package com.stoneistudio.lds.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.lds.infrastructure.repository")
public class DatabaseConfig {
    // 데이터베이스 관련 Bean 설정을 추가할 수 있습니다.
}
