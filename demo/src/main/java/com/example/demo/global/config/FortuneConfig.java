package com.example.demo.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class FortuneConfig {
    @Bean
    public Clock fortuneClock() {
        return Clock.system(ZoneId.of("Asia/Seoul"));
    }
}

