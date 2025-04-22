package com.trust.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    // Permitir CORS (já estava correto)
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:PORT", "http://10.0.2.2:PORT") // Substitui pelo IP real
                        .allowedMethods("*");
            }
        };
    }

    // Permitir acesso a todos os endpoints
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desativa CSRF por agora
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll() // permite login
                        .anyRequest().authenticated()               // outras rotas precisam de autenticação
                );

        return http.build();
    }
}
