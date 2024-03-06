package com.yybf.chenojbackendgateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;

/**
 * 用来处理跨域问题
 * @author yangyibufeng
 * @date 2024/3/6
 */
@Configuration
public class CorsConfig {

        @Bean
        public CorsWebFilter corsFilter() {
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedMethod("*");
            config.setAllowCredentials(true);
            // todo 实际改为线上真实域名、本地域名
            config.setAllowedOriginPatterns(Arrays.asList("*"));
            config.addAllowedHeader("*");
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
            source.registerCorsConfiguration("/**", config);
            return new CorsWebFilter(source);
        }

}