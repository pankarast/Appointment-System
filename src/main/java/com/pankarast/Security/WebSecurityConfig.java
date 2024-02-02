package com.pankarast.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/h2-console/**").permitAll() // Allow H2 console access
                // Secure API paths
                .requestMatchers(antMatcher("/doctors/**")).authenticated()
                .requestMatchers(antMatcher("/patients/**")).authenticated()
                .requestMatchers(antMatcher("/appointments/**")).authenticated()
                .anyRequest().authenticated()
                .and()
                .csrf().ignoringRequestMatchers("/h2-console/**") // Disable CSRF for H2 console
                .and()
                .headers().frameOptions().disable() // Disable frame options for H2 console
                .and()
                .csrf().disable(); // Consider re-enabling CSRF with appropriate exclusions

        return http.build();
    }
}

