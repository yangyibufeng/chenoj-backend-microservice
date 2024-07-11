package com.yybf.chenojbackendcommon.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;

/**
 * 用来操作JWT的工具类
 *
 * @author yangyibufeng
 * @date 2024/7/10
 */

public class JwtUtils {
    // token过期时间 1天
    @Value("${JWT.token-expire}")
    private static int TOKEN_EXPIRE = 60 * 60 * 24;

    // token密钥
    @Value("${JWT.token-secret}")
    private static String TOKEN_SECRET = "BuFengYangYiWeiYuZHongQi";

    /**
     * 生成JWT令牌。
     *
     * @param params 令牌中携带的参数，以键值对形式提供。
     * @return 生成的JWT令牌字符串。
     * @author yangyibufeng
     * @date 2024/7/11 12:33
     */
    public static String generateToken(Map<String, Object> params) {
        // 获取当前时间，用于计算令牌的过期时间
        long currentTime = System.currentTimeMillis();
        // 计算令牌的过期时间，TOKEN_EXPIRE定义了令牌的有效期长度
        long expireTime = currentTime + TOKEN_EXPIRE * 1000L;

        // 使用JJWT库构建JWT令牌
        return Jwts.builder()
                .setIssuer("yybf") // 设置令牌的发行者为"yybf"
                .setIssuedAt(new Date()) // 设置令牌的发行时间为空（使用当前时间）
                .setExpiration(new Date(expireTime)) // 设置令牌的过期时间
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET) // 使用HS512算法和预定义的TOKEN_SECRET对令牌进行签名
                .addClaims(params) // 添加参数到令牌的声明中
                .compact(); // 最后将构建的令牌紧凑化成字符串返回
    }


    /**
     * 根据token获取Claims。
     *
     * @param token 用户令牌，用于验证用户身份。
     * @return 返回解析后的Claims，包含令牌中的信息。
     * @throws Exception 如果token不合法或解析失败，则抛出异常。
     * @author yangyibufeng
     * @date 2024/7/10 16:19
     */
    public static Claims getClaimByToken(String token) throws Exception {
        Claims claims = null;

        // 检查token是否为空或仅包含空白字符
        if (StringUtils.isNotBlank(token)) {
            try {
                // 使用JWT的解析器解析token，并设置签名密钥
                claims = (Claims) Jwts.parser()
                        .setSigningKey(TOKEN_SECRET)
                        .parseClaimsJws(token)
                        .getBody();
            } catch (Exception e) {
                // 解析失败时，抛出异常提示token不合法
                throw new Exception("token不合法");
            }
        }

        return claims;
    }


    /**
     * 用来验证传入的token是否合法
     *
     * @param token: 传入的待检验的token
     * @return boolean: 检验结果  true 表示token合法 ，false 表示token不合法
     * @author yangyibufeng
     * @date 2024/7/10 15:25
     */
    public static boolean verifyToken(String token) {
        // token为空
        if (StringUtils.isBlank(token)) {
            return false;
        }

        try {
            Claims claimByToken = getClaimByToken(token);
            // token过期
            if (claimByToken.getExpiration().before(new java.util.Date())) {
                return false;
            }
        } catch (Exception e) {
            // token不合法
            return false;
        }
        return true;
    }
}