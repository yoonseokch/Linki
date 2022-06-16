package com.linki.linki.auth.common;

import com.linki.linki.member.domain.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final long expireTimeInMS;

    public JwtTokenProvider(@Value("${LINKY_SECRET_KEY}") String secretKey, @Value("${EXPIRE_LENGTH_IN_MILLISECONDS}") long expireTimeInMS) {
        this.secretKey = secretKey;
        this.expireTimeInMS = expireTimeInMS;
    }

    public String createJwtToken(Member member) {

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
}
