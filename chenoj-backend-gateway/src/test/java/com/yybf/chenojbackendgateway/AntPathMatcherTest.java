package com.yybf.chenojbackendgateway;

import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.List;

/**
 * $END$
 *
 * @author yangyibufeng
 * @date 2024/7/22
 */
public class AntPathMatcherTest {
    private static String path = "/api/user/get/login," +
            "/api/user/logout," +
            "/user/register," +
            "api/**/api-docs," +
            "/api/judge/v2/api-docs," +
            "/api/user/login," +
            "/user/getLoginUser," +
            "/api/user/register";
    private static List<String> excludedUrls = Arrays.asList(path.split(","));
    private static AntPathMatcher pathMatcher = new AntPathMatcher();
    public static void main(String[] args) {
        String requestPath = "user/getLoginUser";
        for (String pattern : excludedUrls) {
            if (pathMatcher.match(pattern, requestPath)) {
                System.out.println("规则：" + pattern + "路径" + requestPath + "匹配成功");
            }
        }
    }
}