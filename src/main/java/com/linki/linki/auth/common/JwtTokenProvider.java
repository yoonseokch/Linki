package com.linki.linki.auth.common;

import com.linki.linki.auth.exception.UnauthenticatedException;
import com.linki.linki.member.domain.Member;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.*;

@Component
public class JwtTokenProvider {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String ISSUER_NAME = "LINKY";

    private final String secretKey;
    private final long expireTimeInMS;

    public JwtTokenProvider(@Value("${LINKY_SECRET_KEY}") String secretKey, @Value("${EXPIRE_LENGTH_IN_MILLISECONDS}") long expireTimeInMS) {
        this.secretKey = secretKey;
        this.expireTimeInMS = expireTimeInMS;
    }

    public String createToken(Member member) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expireTimeInMS);

        return Jwts.builder()
                .setSubject(member.getMemberID().toString()) // 사용자
                .setIssuer(ISSUER_NAME)
                .setIssuedAt(now) // 현재 시간 기반으로 생성
                .setExpiration(expiryDate) // 만료 시간 세팅
                .signWith(HS512, secretKey) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
                .compact();

    }

    public String getTokenFromRequest(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String header = httpRequest.getHeader(AUTH_HEADER_NAME);
        if (!isValidFormat(header)){
            throw new UnauthenticatedException("올바른 인증 방식이 아닙니다");
        }
        return tokenFromHeader(header);
    }

    public String getSubject(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthenticatedException("Invalid token\n" + e.getMessage());
        }
    }

    private String tokenFromHeader(String header) {
        return header.replace(TOKEN_PREFIX,"");
    }

    private boolean isValidFormat(String token) {
        return !StringUtils.hasText(token) || token.startsWith(TOKEN_PREFIX);
    }
}
