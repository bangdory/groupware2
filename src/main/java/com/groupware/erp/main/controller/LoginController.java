//package com.groupware.erp.main.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class LoginController {
//
//    @GetMapping("/login")
//    public String login() {
//        return "/login";
//    }
//}

// jwt토큰 생성하는 login 로직 예시
//@PostMapping("/login")
//public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//    // 인증 로직
//    Authentication authentication = authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(loginRequest.getEmpEmail(), loginRequest.getEmpPassword())
//    );
//
//    // JWT 생성
//    String token = jwtUtil.generateToken(authentication.getName());
//    return ResponseEntity.ok(new LoginResponse(token));
//}
//}

