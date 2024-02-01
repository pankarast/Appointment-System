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
                .httpBasic() // Use HTTP Basic authentication for simplicity
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/h2-console/**").permitAll() // Allow h2 console access without authentication
                // Secure API paths
                .requestMatchers(antMatcher("/doctors/**")).permitAll()
                .requestMatchers(antMatcher("/patients/**")).permitAll()
                .requestMatchers(antMatcher("/appointments/**")).permitAll()
//                .antMatchers("/doctors/**").authenticated()
//                .antMatchers("/patients/**").authenticated()
//                .antMatchers("/appointments/**").authenticated()
                // Example of how to permit public access to specific paths
                // .antMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated() // Require authentication for any other request
                .and()
                .csrf().ignoringRequestMatchers("/h2-console/**") // Disable CSRF for h2 console
                .and()
                .headers().frameOptions().disable() // Disable frame options for h2 console
                .and()
                .csrf().disable(); // Optionally disable CSRF protection (consider security implications)

        return http.build();
    }
}
