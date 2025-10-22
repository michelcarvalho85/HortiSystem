package com.HortiSystem.Sistema.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ConsentimentoInterceptor consentimentoInterceptor;

    @Autowired
    private LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // Interceptor de consentimento de LGPD
        registry.addInterceptor(consentimentoInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**", "/js/**", "/images/**",
                        "/politica-privacidade", "/usuario/aceitar",
                        "/usuario/novo", "/login", "/error", "/h2-console/**",
                        "/webjars/**")
                .order(0);

        // Interceptor de logs LGPD (para registrar ações)
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**", "/js/**", "/images/**",
                        "/politica-privacidade", "/usuario/aceitar",
                        "/usuario/novo", "/login", "/error", "/h2-console/**",
                        "/webjars/**")
                .order(1);
    }
}
