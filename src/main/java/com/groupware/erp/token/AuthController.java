package com.groupware.erp.token;


import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwtauth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final  JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("")
    public ResponseEntity<?> validateToken(HttpServletRequest request,
                                           @RequestHeader("Authorization") String token) {
        log.info("여기는 jwt 인증 메소드!! 인증메소드는 정상 실행됐다!! {}", request);

        // 클라이언트 요청에서 jwt토큰 빼기
        String jwtToken = jwtTokenProvider.resolveToken(request);

        log.info("jwtProvider.resolveToken 메소드가 잘 실행되었나요??? {}", jwtToken);
        boolean unValidateToken = jwtTokenProvider.validateToken(jwtToken);

        if (jwtToken == null || !unValidateToken) {
            log.info("jwtProivder.validateToken()가 false입니까??? {}", unValidateToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
        }

        log.info("jwtTokenProvider.validateToken!!!! {}", unValidateToken);


        GrantedAuthority authority = jwtTokenProvider.getAuthoritiesFromToken(jwtToken);
        log.info("jwtTokenProvider.getAuthoritiesFromToken!!!! {}", authority);

        String response = authority.getAuthority();
        log.info("으아아ㅏ!!! authority.getAuthority(): {}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
