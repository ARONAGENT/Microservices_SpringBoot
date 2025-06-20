package com.springJourneyMax.apiGatewayService.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoginGatewayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

       log.info("Logging from Pre Global {}",exchange.getRequest().getURI());
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            log.info("Logging from Post Global {}",exchange.getResponse().getStatusCode());
        }));
    }

    @Override
    public int getOrder() {
        return 5;
    }
}
