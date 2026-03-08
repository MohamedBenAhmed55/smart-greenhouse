package com.polytech.greenhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // 1. Allow your Angular Frontend URL
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));

        // 2. Allow all HTTP methods (GET, POST, PUT, DELETE, OPTIONS)
        corsConfig.addAllowedMethod("*");

        // 3. Allow all headers (Content-Type, Authorization, etc.)
        corsConfig.addAllowedHeader("*");

        // 4. Register this config for all routes (/**)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}