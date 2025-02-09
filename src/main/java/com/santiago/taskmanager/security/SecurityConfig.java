package com.santiago.taskmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**", "/projects/**", "/tasks/**").permitAll() // Public access
                        .requestMatchers("/h2-console/**").permitAll() // Allow H2 console access
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/users/**", "/projects/**", "/tasks/**")) // Disable CSRF for allowed endpoints
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // Allow H2 console in frames
                .formLogin(AbstractHttpConfigurer::disable) // Disable login form
                .httpBasic(AbstractHttpConfigurer::disable); // Disable HTTP Basic Authentication

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}




