package com.tng.regions.service;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class HttpRequestInterceptor implements WebFilter {

    private final Map<String, List<String>> headers = new HashMap<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        exchange.getRequest().getHeaders().forEach(headers::put);
        return chain.filter(exchange);
    }

    public String getHeaderValueByKey(String key){
        return headers.get(key)
                .stream()
                .map(Optional::ofNullable).findFirst()
                .orElseGet(Optional::empty)
                .orElse(null);
    }
}
