package com.springJourneyMax.Microservices.orderService.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@Data
public class User_tracingConfig {

    @Value("${user-tracing_isenabled}")
    private boolean isUserTracingEnabled;
}
