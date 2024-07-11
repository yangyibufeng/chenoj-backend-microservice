package com.yybf.chenojbackendcommon.utils;

import io.jsonwebtoken.Claims;

/**
 * $END$
 * 测试解析token之后得到的内容
 *
 * @author yangyibufeng
 * @date 2024/7/11
 */
public class testClaims {
    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJ5eWJmIiwiaWF0IjoxNzIwNjc0NjQyLCJleHAiOjE3MjA3NjE" +
                "wNDIsInVzZXJBY2NvdW50IjoieHNobiIsInVzZXJSb2xlIjoiYWRtaW4iLCJ1c2VySWQiOjE3NTA3NTA1OTkyMzE1OTA0MDF" +
                "9.15I9lxH1UOgLimeRqFpXI6YEtE8OPnw7YC_MFAoLJndCsokT7y0_BF0fWaYr6Cnyz0QJLoBDMXA2xdMU_WYK1g";

        try {
            Claims claims = JwtUtils.getClaimByToken(token);
            System.out.println(claims);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}