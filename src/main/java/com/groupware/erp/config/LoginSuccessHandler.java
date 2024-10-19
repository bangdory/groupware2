package com.groupware.erp.config;

import com.groupware.erp.token.JwtTokenDTO;
import com.groupware.erp.token.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final LoginUserDetailService loginUserDetailService;

    public LoginSuccessHandler(JwtTokenProvider jwtTokenProvider, LoginUserDetailService loginUserDetailService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginUserDetailService = loginUserDetailService;
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) throws IOException, ServletException {
        // jwt 토큰 생성
        JwtTokenDTO jwtTokenDTO = jwtTokenProvider.generateToken(authentication);

        // Principal 가져오기
        Object principal = authentication.getPrincipal();
        CustomUserDetails customUserDetails;

        // principal이 CustomUserDetails이면 캐스팅
        if (principal instanceof CustomUserDetails) {
            customUserDetails = (CustomUserDetails) principal;
        } else if (principal instanceof String) {
            // principal이 String일 때, username으로 CustomUserDetails 객체 로드
            String username = (String) principal;
            customUserDetails = (CustomUserDetails) loginUserDetailService.loadUserByUsername(username);
        } else {
            throw new IllegalArgumentException("Unknown principal type: " + principal.getClass().getName());
        }

        // 첫 로그인인지 체크
        if (customUserDetails.getPassword().equals(customUserDetails.getUsername())) { // username = empNo
            // 첫로그인 = 비밀번호변경 페이지
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // 헤더에 JWT 토큰 추가
            response.setHeader("Authorization", "Bearer " + jwtTokenDTO.getAccessToken());

            PrintWriter writer = response.getWriter();
            // /changePassword = 비밀번호변경페이지 url
            writer.write("{\"redirectUrl\": \"/\", \"accessToken\": \"" + jwtTokenDTO.getAccessToken() + "\"}");
            writer.flush();
        } else {
            // 비밀번호가 다르면 JWT 토큰 응답
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // 헤더에 JWT 토큰 추가
            response.setHeader("Authorization", "Bearer " + jwtTokenDTO.getAccessToken());

            PrintWriter writer = response.getWriter();
            writer.write("{\"accessToken\":\"" + jwtTokenDTO.getAccessToken() + "\", \"refreshToken\":\"" + jwtTokenDTO.getRefreshToken() + "\"}");
            writer.flush();
        }
    }


//    @Override
//    public void onAuthenticationSuccess(final HttpServletRequest request,
//                                        final HttpServletResponse response,
//                                        final Authentication authentication) throws IOException, ServletException {
//        // jwt 토큰 생성
//        JwtTokenDTO jwtTokenDTO = jwtTokenProvider.generateToken(authentication);
//
//        // userdetails에서 사용자정보가져오기
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//
//        // 첫 로그인인지 체크
//        if (customUserDetails.getPassword().equals(customUserDetails.getUsername())) { // username = empNo
//            // 첫로그인 = 비밀번호변경 페이지
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//
//            // 헤더에 JWT 토큰 추가
//            response.setHeader("Authorization", "Bearer " + jwtTokenDTO.getAccessToken());
//
//            PrintWriter writer = response.getWriter();
//            // /changePassword = 비밀번호변경페이지 url
//            writer.write("{\"redirectUrl\": \"/\", \"accessToken\": \"" + jwtTokenDTO.getAccessToken() + "\"}");
//            writer.flush();
//        } else {
//            // 비밀번호 다르면 jwt 토큰 응답
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//
//            // 헤더에 JWT 토큰 추가
//            response.setHeader("Authorization", "Bearer " + jwtTokenDTO.getAccessToken());
//
//            PrintWriter writer = response.getWriter();
//            writer.write("{\"accessToken\":\"" + jwtTokenDTO.getAccessToken() + "\", \"refreshToken\":\"" + jwtTokenDTO.getRefreshToken() + "\"}");
//            writer.flush();
//        }
//
//    }
}
