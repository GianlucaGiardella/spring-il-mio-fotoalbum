package com.experis.course.springilmiofotoalbum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/users").hasAuthority("ADMIN")
                .requestMatchers("/categories").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/photos/**").hasAuthority("ADMIN")
                .requestMatchers("/photos", "/photos/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout();

        http.csrf().disable();

        return http.build();
    }
}
