package com.reactive.webflux.demo.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CalculatorRouterConfig {
    private final CalculatorHandler handler;
}
