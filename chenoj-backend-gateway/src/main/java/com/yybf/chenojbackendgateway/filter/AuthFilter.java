package com.yybf.chenojbackendgateway.filter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yybf.chenojbackendcommon.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于JWT的全局统一鉴权
 *
 * @author yangyibufeng
 * @date 2024/7/10
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    //如果配置文件中的字符串是用,隔开的 springboot 在value时会自动变成 List<String> 的数组
    @Value("#{'${gateway.excludedUrls}'.split(',')}")
    private List<String> excludedUrls; //配置不需要校验的链接

    // 创建一个AntPathMatcher对象，可以允许路径通过通配符进行比较
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 获取请求路径, 判断是否放行
        String requestPath = exchange.getRequest().getPath().value();
        log.info("requestPath:{}", requestPath);
        for (String pattern : excludedUrls) {
            if (pathMatcher.match(pattern, requestPath)) {
                log.info("放行规则:{}", pattern);
                return chain.filter(exchange);
            }
        }
//        if (excludedUrls.contains(requestPath)) { // 如果属于放行的路径，直接放行
//            return chain.filter(exchange);
//        }

        // 2. 获取token，并判断是否有效
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (StringUtils.isNotEmpty(token)) { // 如果token不为空，需要进行特殊处理
            token = token.replace("Banrer", "");
        }
        // 获取到即将发送的请求
        ServerHttpResponse response = exchange.getResponse();
        log.info("token:{}", token);

        // 判断token是否合法
        boolean verifyToken = JwtUtils.verifyToken(token);

        if (!verifyToken) {// token不合法
            log.error("token不合法");
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("errCode", 401);
            responseData.put("errMessage", "用户未登录");
            // 直接调用方法处理错误信息，不在向下传递
            return responseError(response, responseData);
        }

        return chain.filter(exchange);
    }

    /**
     * 用来相应错误数据，将传入的 map 转换为 Json
     *
     * @param response: 即将要发送的HTTP响应
     * @param responseData: 错误的具体信息
     * @return reactor.core.publisher.Mono<java.lang.Void>:
     * @author yangyibufeng
     * @date 2024/7/10 14:43
     */
    private Mono<Void> responseError(ServerHttpResponse response, Map<String, Object> responseData) {
        // 将传入的responseData 转换为 json
        ObjectMapper objectMapper = new ObjectMapper();
        // 创建一个byte数组用来存储转换后的json数据
        byte[] data = new byte[0];

        try {
            data = objectMapper.writeValueAsBytes(responseData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 输出错误数据到页面
        DataBuffer buffer = response.bufferFactory().wrap(data);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        log.error("response error:{}", response);
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * 获取当前bean的排序优先级。
     *
     * 该方法重写了Ordered接口的getOrder方法，以指定当前bean的执行顺序。
     * 返回的优先级越高，bean的初始化越晚；优先级越低，初始化越早。
     * LOWEST_PRECEDENCE常量表示最低的优先级，意味着这个bean将在所有其他具有排序能力的bean之后初始化。
     *
     * @return int 返回ORDER.LOWEST_PRECEDENCE，表示这个bean具有最低的优先级。
     * @author yangyibufeng
     * @date 2024/7/10 15:16
     */

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}