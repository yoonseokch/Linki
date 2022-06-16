package com.linki.linki.auth.common;

import com.linki.linki.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenProvider {

    private final String secretKey;
    private final long expireTimeInMS;

    public JwtTokenProvider(@Value("${LINKY_SECRET_KEY}") String secretKey,@Value("${EXPIRE_LENGTH_IN_MILLISECONDS}") long expireTimeInMS) {
        this.secretKey = secretKey;
        this.expireTimeInMS = expireTimeInMS;
    }

    public String createJwtToken(Member member) {
        return null;
    }
}
