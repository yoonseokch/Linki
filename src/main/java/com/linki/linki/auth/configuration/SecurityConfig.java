package com.linki.linki.auth.configuration;

import com.linki.linki.auth.common.JwtAuthenticationFilter;
import com.linki.linki.auth.common.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            // -- Static resources
            "/css/**",
            "/images/**",
            "/js/**",
            // -- Swagger UI v2
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (Open API)
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService service;

    // TODO 멤버 서비스에 password encoder를 놓는게 합당한가 아니면 Spring security측에 놓는게 합당한가
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and()
                .httpBasic().disable() // api 서버기 때문에 로그인창이 필요 없음
                .csrf().disable() // csrf 방어 필요 없음
                .sessionManagement() // JWT를 사용하기에 세션 필요없음
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/member").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui/").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, service), UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
