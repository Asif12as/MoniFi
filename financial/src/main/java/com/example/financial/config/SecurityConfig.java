package com.example.financial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // For learning purposes, we'll disable CSRF and use HTTP Basic auth
        // In a real application, you would want more robust security!
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/transactions/**").authenticated()
                .antMatchers("/api/alerts/**").authenticated()
                .antMatchers("/api/rules/**").authenticated()
                .antMatchers("/api/accounts/**").authenticated()
                .antMatchers("/api/users/**").authenticated()
                .anyRequest().permitAll()
            .and()
            .httpBasic();
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}