package com.yybf.chenojbackendgateway.filter;

import cn.hutool.core.text.AntPathMatcher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 用于全局校验，过滤
 *
 * @author yangyibufeng
 * @date 2024/3/6
 */
@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String path = serverHttpRequest.getURI().getPath();
        // 通过判断路径中是否包含inner，只允许内部访问
        // 这里会过滤所有讲过网关的请求
        if (antPathMatcher.match("/**/inner/**", path)) {
            ServerHttpResponse response = exchange.getResponse();
            // 设置请求的响应码
            response.setStatusCode(HttpStatus.FORBIDDEN);
            // 通过response来创建一个工厂类，然后创建一个DateBuffer对象来传递信息，最后将dateBuffer返回
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap("无权限".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));
        }

        // todo 全局统一权限校验

        return chain.filter(exchange);
    }

    /**
     * @return int:
     * @author yangyibufeng
     * @description 设置优先级为最高
     * @date 2024/3/6 19:50
     */
    @Override
    public int getOrder() {
        return 0;
    }
}