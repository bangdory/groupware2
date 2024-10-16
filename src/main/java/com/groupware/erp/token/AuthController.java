package com.groupware.erp.token;


import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwtauth")
public class AuthController {

    private final  JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("")
    public ResponseEntity<?> validateToken(HttpServletRequest request,
                                           @RequestHeader("Authorization") String token) {
        // 클라이언트 요청에서 jwt토큰 빼기
        String jwtToken = jwtTokenProvider.resolveToken(request);

        if (jwtToken == null || !jwtTokenProvider.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
        }

        GrantedAuthority authority = jwtTokenProvider.getAuthoritiesFromToken(jwtToken);

        String response = authority.getAuthority();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
