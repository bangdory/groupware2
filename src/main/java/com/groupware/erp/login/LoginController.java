package com.groupware.erp.login;

import com.groupware.erp.login.annualLeave.AnnualLeaveCalculator;
import com.groupware.erp.login.annualLeave.AnnualLeaveService;
import com.groupware.erp.token.JwtTokenDTO;
import com.groupware.erp.token.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AnnualLeaveService annualLeaveService;

    public LoginController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @GetMapping()
    public String loginPage(){
        return "login";
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        // DB에서 사용자 정보 가져오기
        LoginEntity user = loginService.findByEmpNo(loginDTO.getEmpNo());

        // 연차 계산 객체 생성
        AnnualLeaveCalculator calculator = new AnnualLeaveCalculator();

        // user 객체 읽기
        log.info(user.toString());

        // 입사 일 가져오기
        LocalDate hireDate = user.getEmpHiredate();
        int useAnn = user.getAnnualLeaveEntities().getUseAnn();
        int pendingAnn = user.getAnnualLeaveEntities().getPendingAnn();

        // empNo, 총 연차일을 기준으로 AnnualLeave 테이블 데이터 업데이트
        annualLeaveService.updateTotalAnn(user.getEmpNo(), hireDate, useAnn,pendingAnn);

        log.info("메서드는 불러왔다!!!! 머시 문젠디!!!");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        if (user.getEmpPassword().equals(loginDTO.getEmpNo())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"redirectUrl\":\"/login/changePassword\"}");
        }

        // 사용자 인증 로직
        Authentication authentication = authenticateUser(loginDTO);

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        //JWT 생성
        JwtTokenDTO jwtTokenDTO = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(jwtTokenDTO);
    }

    private Authentication authenticateUser(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmpNo(), loginDTO.getEmpPassword());
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @GetMapping("/changePassword")
    public String changePasswordPage(@RequestParam(name = "empNo") String empNo,Model model) {

        log.info("비밀번호 바꾸셈");
        log.info("empNo: " + empNo);
        model.addAttribute("empNo", empNo);
        return "login/changePassword";
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        // DB에서 사용자 정보 가져오기
        LoginEntity user = loginService.findByEmpNo(changePasswordDTO.getEmpNo());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        // 비밀번호변경 로직 service에서 호출
        boolean isChanged = loginService.updatePassword(
                changePasswordDTO.getEmpNo(),
                changePasswordDTO.getOldPassword(),
                changePasswordDTO.getNewPassword()
        );

        if (isChanged) {
            log.info("비밀번호 변경 : empNo={}", changePasswordDTO.getEmpNo());
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            log.info("비밀번호 변경 실패 : 이미 사용된 비밀번호.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않거나 이미 사용된 비밀번호");
        }
    }
}
