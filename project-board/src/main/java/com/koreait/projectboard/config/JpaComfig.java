package com.koreait.projectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaComfig {
    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.of("관리자"); //스프링 시큐리티로 인증기능을 붙이게될때 수정할 것임
    }
}
