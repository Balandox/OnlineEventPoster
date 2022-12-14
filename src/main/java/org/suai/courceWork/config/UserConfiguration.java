package org.suai.courceWork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.suai.courceWork.models.entities.User;

@Configuration
public class UserConfiguration {

    @Bean
    public User getUser() {
        return new User();
    }

}
