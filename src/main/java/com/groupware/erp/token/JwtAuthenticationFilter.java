package com.groupware.erp.token;

import com.groupware.erp.config.LoginSuccessHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, LoginSuccessHandler loginSuccessHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // JWT 검증을 제외할 경로 목록 (정적 리소스 자원)
        if (requestURI.startsWith("/assets/") || requestURI.startsWith("/css/") || requestURI.startsWith("/js/") ||
                requestURI.startsWith("/img/") || requestURI.startsWith("/vendor/") || requestURI.startsWith("/scss/") ||
                requestURI.equals("/login") || requestURI.equals("/login/changePassword")) {
            // 필터를 통과시키고 더 이상 JWT 검증을 하지 않음
            filterChain.doFilter(request, response);
            return;
        }

        // 1. JWT 토큰을 요청 헤더에서 가져옴
        String token = jwtTokenProvider.resolveToken(request);

        // 2. JWT 토큰이 유효한지 확인
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 3. 토큰에서 사용자 정보 추출 (예: username, email, department, grade 등)
            String username = jwtTokenProvider.getUsernameFromToken(token);
            String empEmail = jwtTokenProvider.getEmailFromToken(token);
            String department = jwtTokenProvider.getDepartmentFromToken(token);
            String empGrade = jwtTokenProvider.getGradeFromToken(token);

            // 4. Authentication 객체 생성 (권한도 함께 설정 가능)
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(username, empEmail, department, empGrade, token);

            // 5. 인증을 SecurityContext에 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 인증 후 후속처리 (successHandler 호출)
            loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        }

        // 6. 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }


//        // Authorization 헤더에서 토큰 가져오기
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String jwt = authHeader.substring(7); // Bearer 이후 토큰만 추출
//
//            // JWT 검증하기
//            if (jwtTokenProvider.validateToken(jwt)) {
//                String username = jwtTokenProvider.getUsernameFromToken(jwt); //사용자이름
//                CustomUserDetails customUserDetails = (CustomUserDetails) loginUserDetailService.loadUserByUsername(username);
//
//                // SecurityContext 에 사용자 정보 설정
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
}

