package com.groupware.erp.config;

import com.groupware.erp.token.JwtTokenDTO;
import com.groupware.erp.token.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) throws IOException, ServletException {
        // jwt 토큰 생성
        JwtTokenDTO jwtTokenDTO = jwtTokenProvider.generateToken(authentication);

        // userdetails에서 사용자정보가져오기
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // 첫 로그인인지 체크
        if (customUserDetails.getPassword().equals(customUserDetails.getUsername())) { // username = empNo
            // 첫로그인 = 비밀번호변경 페이지
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            // /changePassword = 비밀번호변경페이지 url
            writer.write("{\"redirectUrl\": \"/changePassword\", \"accessToken\": \"" + jwtTokenDTO.getAccessToken() + "\"}");
            writer.flush();
        } else {
            // 비밀번호 다르면 jwt 토큰 응답
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write("{\"accessToken\":\"" + jwtTokenDTO.getAccessToken() + "\", \"refreshToken\":\"" + jwtTokenDTO.getRefreshToken() + "\"}");
            writer.flush();
        }

    }
}
