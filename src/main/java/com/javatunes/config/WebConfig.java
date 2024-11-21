package com.javatunes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешаем все пути
                .allowedOrigins("http://localhost:5173") // Разрешаем ваш фронтенд
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешаем методы
                .allowedHeaders("*") // Разрешаем все заголовки
                .allowCredentials(true); // разрешаем отправку учетных данных, если это нужно

    }
}