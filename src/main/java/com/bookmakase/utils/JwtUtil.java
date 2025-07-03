package com.bookmakase.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    // 비밀 도장을 만드는 단계
    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //토큰의 주인이 누구인지를 묻는 함수
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // 기존 토큰이 언제까지 유효한지 묻는 함수
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final  Claims claims = getAllClaimsFromToken(token); // 편지 전체
        return claimsResolver.apply(claims); // 원하는 한 글귀만 읽기
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())   // 우리만 아는 비밀 열쇠
                .build()
                .parseClaimsJws(token)            // 토큰 열어보기 (서명 검증 포함)
                .getBody();                       // ← 클레임(본문)만 반환
    }

    // 토큰이 유효기간이 지났니? 묻는 함수
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // 로그인 성공 시 엑세스 토큰을 발급하는 함수
    public String generateToken(UserDetails userDetails) {
        return doGenerateToken(userDetails.getUsername(), expiration);
    }

    // 로그인 성공 시 리프레쉬 토큰을 발급하는 함수
    public String generateRefreshToken(UserDetails userDetails) {
        return doGenerateToken(userDetails.getUsername(), refreshExpiration);
    }


    private String doGenerateToken(String username, Long expirationTime) {
        Claims claims = Jwts.claims();
        claims.setSubject(username); // 토큰 주인(사용자 이름)을 기록

        return Jwts.builder()
                .setClaims(claims) // 주인 정보 넣고
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000)) // 만료 시간
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 비밀 키로 서명
                .compact(); // 문자열 형태로 완성
    }


    //이 토큰을 신뢰할 수 있는지 판단하는 함수
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
