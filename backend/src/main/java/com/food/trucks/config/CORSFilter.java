package com.food.trucks.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;

@Configuration
public class CORSFilter extends CorsFilter {

    public CORSFilter(CorsConfigurationSource source) {
        super((CorsConfigurationSource) source);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Headers",
                "Access-Control-Allow-Origin, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        if (response.getHeader("Access-Control-Allow-Origin") == null)
            response.addHeader("Access-Control-Allow-Origin", "*");
        filterChain.doFilter(request, response);
    }

}