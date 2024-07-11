package com.yybf.chenojbackendcommon.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class JwtUtilsTest {

    @BeforeEach
    void setUp() {
        // If there's any setup needed that involves Spring beans, do it here.
    }

    @Test
    void testGetClaimByTokenValid() throws Exception {
        Claims claims = Jwts.claims().setSubject("testSubject");
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 10000))
                .signWith(SignatureAlgorithm.HS512, "BuFengYangYiWeiYuZHongQi".getBytes())
                .compact();

        Claims resultClaims = JwtUtils.getClaimByToken(token);
        System.out.println(resultClaims);
//        assertNotNull(resultClaims);
//        assertEquals("testSubject", resultClaims.getSubject());
    }

    @Test
    void testGetClaimByTokenInvalid() {
        assertThrows(Exception.class, () -> JwtUtils.getClaimByToken("invalidToken"));
    }

    @Test
    void testGetClaimByTokenEmpty() {
        assertThrows(Exception.class, () -> JwtUtils.getClaimByToken(""));
    }

    @Test
    void testGetClaimByTokenBlank() {
        assertThrows(Exception.class, () -> JwtUtils.getClaimByToken("   "));
    }
}
