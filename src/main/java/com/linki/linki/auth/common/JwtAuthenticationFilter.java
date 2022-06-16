package com.linki.linki.auth.common;

import com.linki.linki.auth.dto.UnauthenticatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            System.out.println("안녕");
            String token = jwtTokenProvider.getTokenFromRequest(request);
            filterChain.doFilter(request, response);

        } catch (UnauthenticatedException e){
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, Collections.singletonMap("message", e.getMessage()).toString());
        }
    }
}
