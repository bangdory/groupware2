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

        String username = jwtTokenProvider.getUsernameFromToken(jwtToken);
        GrantedAuthority authority = jwtTokenProvider.getAuthoritiesFromToken(jwtToken);
        String email = jwtTokenProvider.getEmailFromToken(jwtToken);
        String department = jwtTokenProvider.getDepartmentFromToken(jwtToken);
        String grade = jwtTokenProvider.getGradeFromToken(jwtToken);

        String authorityString = authority.getAuthority();

        // 사용자정보, 권한 반환
        AuthResponse response = new AuthResponse(username,authorityString,email,department,grade);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

@Data
class AuthResponse {
    private String username;
    private String authorities;
    private String email;
    private String department;
    private String grade;

    public AuthResponse(String username, String authorities, String email, String department, String grade) {
        this.username = username;
        this.authorities = authorities;
        this.email = email;
        this.department = department;
        this.grade = grade;
    }

}