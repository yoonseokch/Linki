package com.linki.linki.auth.common;

import com.linki.linki.auth.dto.UnauthenticatedException;
import com.linki.linki.member.domain.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtTokenProvider {

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
                .setIssuer("Linky")
                .setIssuedAt(now) // 현재 시간 기반으로 생성
                .setExpiration(expiryDate) // 만료 시간 세팅
                .signWith(SignatureAlgorithm.HS512, secretKey) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
                .compact();

    }

    public String getTokenFromRequest(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String header = httpRequest.getHeader("Authorization");
        System.out.println(header);
        if (isInvalidHeader(header)){
            throw new UnauthenticatedException("올바른 인증 방식이 아닙니다");
        }
        return header;
    }

    private boolean isInvalidHeader(String token) {

        return !StringUtils.hasText(token) || token.equals("Bearer");
    }
}
