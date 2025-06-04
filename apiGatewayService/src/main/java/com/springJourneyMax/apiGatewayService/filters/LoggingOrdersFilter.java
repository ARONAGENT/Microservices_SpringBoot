package com.springJourneyMax.apiGatewayService.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingOrdersFilter extends AbstractGatewayFilterFactory<LoggingOrdersFilter.Config> {

    public LoggingOrdersFilter() {
        super(Config.class); // ✅ Use default constructor and pass Config class directly
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Logging from Pre Orders: {}", exchange.getRequest().getURI());
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() ->
                            log.info("Logging from Post Orders: Status code = {}", exchange.getResponse().getStatusCode())
                    ));
        };
    }

    public static class Config {
        // Add config fields if needed (e.g., logRequest, logResponse flags)
    }
}
