package com.springJourneyMax.Microservices.orderService.config;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Capability capability(final MeterRegistry registry) {
        return new MicrometerCapability(registry);
    }
}
