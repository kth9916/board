package com.kth.board.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);

        if (token != null && jwtProvider.validateToken(token)) {
            // check access token
            token = token.split(" ")[1].trim();
            Authentication auth = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String accessToken = jwtProvider.resolveToken(request);
//        String refreshToken = jwtProvider.resolveRefreshToken(request);
//
//        Optional<Member> member = signService.findMember(jwtProvider.getAccount(accessToken));
//
//        if (accessToken != null && jwtProvider.validateToken(accessToken)) {
//            // check access token
//            accessToken = accessToken.split(" ")[1].trim();
//            Authentication auth = jwtProvider.getAuthentication(accessToken);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//        // 어세스 토큰 만료된 상황
//        else if(!jwtProvider.validateToken(accessToken) && refreshToken != null)
//            // 재발급 후 , 컨텍스트에 넣기
//            // 리프레쉬 토큰 검증
//        {
//            try {
//                Token validateRefreshToken = jwtProvider.validRefreshToken(member, refreshToken)
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//
//        filterChain.doFilter(request, response);
//    }
}
