package com.dev.backend.ApiGateway.filters;

import com.dev.backend.ApiGateway.service.JwtService;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {


    private final JwtService jwtService;

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

//            if(!config.isEnabled) return chain.filter(exchange);
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if(authorizationHeader == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String bearerToken = authorizationHeader.split("Bearer ")[1];

            Long userId = jwtService.getUserIdFromToken(bearerToken);

            ServerHttpRequest mutatedRequest = exchange.getRequest()
                    .mutate()
                    .header("X-user-id", userId.toString())
                    .build();

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(mutatedRequest)
                    .build();

            return chain.filter(mutatedExchange);

//            exchange.getRequest()// This is the way to set the header for the downStream services ,so we can pass these headers anywhere in the downStream Services
//                    .mutate()
//                    .header("X-user-id", userId.toString())
//                    .build();


//            return chain.filter(exchange);
        };
    }

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    public static class Config {
        private boolean isEnabled;
    }
}
