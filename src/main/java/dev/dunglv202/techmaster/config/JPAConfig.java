package dev.dunglv202.techmaster.config;

import dev.dunglv202.techmaster.entity.User;
import dev.dunglv202.techmaster.model.auth.SecurityAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JPAConfig {
    @Bean
    public AuditorAware<User> auditorProvider() {
        return new SecurityAuditorAware();
    }
}
