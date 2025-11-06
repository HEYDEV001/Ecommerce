package com.dev.backend.ApiGateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoggingFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // This is preFilter
        log.info("Logging from PreGlobal : {}", exchange.getRequest().getURI());
        return chain.filter(exchange).then(Mono.fromRunnable(new Runnable() {
            @Override
            public void run() {
                log.info("Global PostFilter executed {}", exchange.getResponse().getStatusCode());
            }
        })); // this will only tell to go to the next filter
    }

    @Override
    public int getOrder() {
        //  This will give the order in the filterChain  like here this filter is on number 5 in the filterChain
        return 5;
    }
}
