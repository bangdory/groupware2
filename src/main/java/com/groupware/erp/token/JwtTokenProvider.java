package com.groupware.erp.token;

import com.groupware.erp.config.CustomUserDetails;
import com.groupware.erp.config.LoginUserDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    private final Set<String> invalidatedTokens = new HashSet<>(); // 토큰무효화저장
    private final LoginUserDetailService loginUserDetailService;

    // secret 값 key에 저장
    public JwtTokenProvider(@Value("${jwt.secret}")String secretKey, LoginUserDetailService loginUserDetailService) {
        this.loginUserDetailService = loginUserDetailService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);

    }

    // employee 정보로 accesstoken, refreshtoken 생성
    public JwtTokenDTO generateToken(Authentication authentication) {

        // 권한
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // 임시 추가 10_13일 박범수
//        CustomUserDetails cud = (CustomUserDetails) authentication.getPrincipal();
//        String email = cud.getEmail();
//        String department = cud.getDepartment();
//        String empGrade = cud.getEmpGrade();

        Object principal = authentication.getPrincipal();

        String accessToken = "";

// Principal이 String인 경우 처리
        if (principal instanceof String) {
            String username = (String) principal;

            // username을 사용하여 CustomUserDetails 객체를 로드
            CustomUserDetails customUserDetails = (CustomUserDetails) loginUserDetailService.loadUserByUsername(username);

            // JWT 생성
            Date accessTokenExpiresIn = new Date(now + 1000 * 60 * 60); // 토큰 만료시간 1시간
            accessToken = Jwts.builder()
                    .setSubject(customUserDetails.getUsername()) // 사원번호 (username)
                    .claim("auth", authorities) // 권한
                    .claim("empEmail", customUserDetails.getEmail()) // 이메일
                    .claim("department", customUserDetails.getDepartment()) // 부서
                    .claim("empGrade", customUserDetails.getEmpGrade()) // 직급
                    .setExpiration(accessTokenExpiresIn)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } else if (principal instanceof CustomUserDetails) {
            // Principal이 이미 CustomUserDetails인 경우 바로 사용 가능
            CustomUserDetails customUserDetails = (CustomUserDetails) principal;

            // JWT 생성
            Date accessTokenExpiresIn = new Date(now + 1000 * 60 * 60); // 토큰 만료시간 1시간
            accessToken = Jwts.builder()
                    .setSubject(customUserDetails.getUsername()) // 사원번호 (username)
                    .claim("auth", authorities) // 권한
                    .claim("empEmail", customUserDetails.getEmail()) // 이메일
                    .claim("department", customUserDetails.getDepartment()) // 부서
                    .claim("empGrade", customUserDetails.getEmpGrade()) // 직급
                    .setExpiration(accessTokenExpiresIn)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } else {
            throw new IllegalArgumentException("Unknown principal type: " + principal.getClass().getName());
        }


//        // Access Token
//        Date accessTokenExpiresIn = new Date(now + 1000 * 60 * 60); // 토큰만료시간 1시간
//        String accessToken = Jwts.builder()
//                .setSubject(authentication.getName()) // 사원번호(username)
//                .claim("auth",authorities) // 권한
//                .claim("empEmail", ((CustomUserDetails) authentication.getPrincipal()).getEmail()) // 이메일
//                .claim("department",  ((CustomUserDetails) authentication.getPrincipal()).getDepartment()) // 부서
//                .claim("empGrade",  ((CustomUserDetails) authentication.getPrincipal()).getEmpGrade()) // 직급
//                .setExpiration(accessTokenExpiresIn)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();


        // Refresh Token
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 1000 * 60 * 60 * 24)) // 토큰만료시간 24시간
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        log.info("generated access token for user: {}", authentication.getName());

        return JwtTokenDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validateToken(String token) {
        if (isTokenInvalid(token)){
            log.error("JWT token is invalid (invalidated): {}", token);
            return false; // 무효화토큰 체크
        }
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true; // 토큰유효
        } catch (SignatureException | ExpiredJwtException e) {
            log.error("JWT token is invalid: ",e.getMessage());
            return false; // 토큰유효x 혹은 만료
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)  // 비밀 키 설정
                    .build()
                    .parseClaimsJws(token)  // 토큰 파싱 및 검증
                    .getBody();

            return claims.getSubject();  // "sub" 클레임에서 사용자 이름 추출
        } catch (JwtException | IllegalArgumentException e) {
            // 예외 발생 시 로그 출력
            log.error("토큰 파싱 오류: {}", e.getMessage());
            return null;  // 토큰이 유효하지 않으면 null 반환
        }
    }


//    public String getUsernameFromToken(String token) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getSubject();
//    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("empEmail", String.class);
    }

    public String getDepartmentFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("department", String.class);
    }

    public String getGradeFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("empGrade", String.class);
    }

    public boolean invalidateToken(String token) {
        return invalidatedTokens.add(token); // 무효화목록에 토큰추가
    }

    public boolean isTokenInvalid(String token) {
        return invalidatedTokens.contains(token); // 무효호ㅛㅏ목록에 토큰 유무 체크
    }

    // JWT 토큰을 요청 헤더에서 가져오는 메소드
    public String resolveToken(HttpServletRequest request) {
        // "Authorization" 헤더에서 "Bearer "로 시작하는 부분을 추출
        String bearerToken = request.getHeader("Authorization");

        log.info("Authorization 헤더: " + bearerToken);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // "Bearer " 이후 부분만 반환
        }

        log.error("토큰이 없거나 형식이 잘못되었습니다.");
        return null;  // 토큰이 없거나 형식이 잘못된 경우 null 반환
    }


}