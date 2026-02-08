package com.temp.apiGateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class UserHeaderFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication())
                .filter(authentication -> authentication != null && authentication.isAuthenticated())
                .flatMap(authentication -> {
                    if (authentication.getPrincipal() instanceof Jwt) {
                        Jwt jwt = (Jwt) authentication.getPrincipal();
                        
                        String userId = jwt.getSubject();
                        Object role = jwt.getClaims().get("role");

                        ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate();
                        
                        if (userId != null) {
                            requestBuilder.header("X-User-Id", userId);
                        }
                        
                        if (role != null) {
                            requestBuilder.header("X-User-Role", role.toString());
                        }

                        return chain.filter(exchange.mutate().request(requestBuilder.build()).build());
                    }
                    return chain.filter(exchange);
                })
                .switchIfEmpty(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
