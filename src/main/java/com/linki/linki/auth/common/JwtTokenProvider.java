package com.linki.linki.auth.common;

import com.linki.linki.auth.exception.UnauthenticatedException;
import com.linki.linki.member.domain.Member;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

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

    public Optional<String> getHeaderIfExist(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String header = httpRequest.getHeader(AUTH_HEADER_NAME);
        return Optional.ofNullable(header);
    }

    public String getSubjectFromHeader(String header) {
        if (!isValidHeader(header)) {
            throw new UnauthenticatedException("인증 형식이 올바르지 않습니다.");
        }
        String token = tokenFromHeader(header);
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthenticatedException(e.getMessage());
        }
    }

    private String tokenFromHeader(String header) {
        return header.replace(TOKEN_PREFIX, "");
    }

    private boolean isValidHeader(String token) {
        return StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX);
    }
}
