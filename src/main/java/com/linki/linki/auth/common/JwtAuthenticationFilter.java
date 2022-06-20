package com.linki.linki.auth.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linki.linki.auth.dto.UnauthenticatedResponse;
import com.linki.linki.auth.exception.UnauthenticatedException;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.getTokenFromRequest(request);
            Optional.ofNullable(token)
                    .map(jwtTokenProvider::getSubject)
                    .map(userDetailsService::loadUserByUsername)
                    .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails,
                            "",
                            userDetails.getAuthorities()))
                    .ifPresent(authentication -> {
                        SecurityContext newContext = SecurityContextHolder.createEmptyContext();
                        newContext.setAuthentication(authentication);
                    });

        } catch (UnauthenticatedException e){
            writeUnauthenticatedResponse(response,e);
        }
    }

    private void writeUnauthenticatedResponse(HttpServletResponse response,UnauthenticatedException e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        UnauthenticatedResponse data = new UnauthenticatedResponse(e.getMessage());
        String body = objectMapper.writeValueAsString(data);
        response.getWriter().write(body);
    }
}
