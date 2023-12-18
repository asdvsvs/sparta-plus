package com.sparta.plus.common.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "토큰 검증 및 인가")
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        if (request.getCookies() != null) {
            Cookie authCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("Authorization"))
                .findFirst()
                .orElse(null);

            if (authCookie != null) {
                token = URLDecoder.decode(authCookie.getValue(), "UTF-8");
                log.info(token);
                token = token.substring(6);
                log.info(token);
            } else {
                log.error("인증 쿠키가 없습니다.");
            }

            // 시큐리티 과정
            if (token != null) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                Claims memberInfo = jwtUtil.getUserInfoFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(
                    memberInfo.getSubject());
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null);
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            }
        }

        filterChain.doFilter(request, response);
    }
}
