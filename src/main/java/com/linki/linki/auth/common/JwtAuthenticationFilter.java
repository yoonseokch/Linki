package com.linki.linki.auth.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linki.linki.auth.dto.UnauthenticatedResponse;
import com.linki.linki.auth.exception.UnauthenticatedException;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> header = jwtTokenProvider.getHeaderIfExist(request);
        try {
            header.map(jwtTokenProvider::getSubjectFromHeader)
                    .map(userDetailsService::loadUserByUsername)
                    .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails,
                            userDetails.getPassword(),
                            userDetails.getAuthorities()))
                    .ifPresent(authentication -> {
                        SecurityContext context = SecurityContextHolder.createEmptyContext();
                        context.setAuthentication(authentication);
                        SecurityContextHolder.setContext(context);
                    });
            filterChain.doFilter(request, response);
        } catch (UnauthenticatedException e) {
            sendUnauthenticatedResponse(response, e);
        }
    }

    private void sendUnauthenticatedResponse(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        UnauthenticatedResponse data = new UnauthenticatedResponse(e.getMessage());
        String body = objectMapper.writeValueAsString(data);
        response.getWriter().write(body);
    }
}
