package com.groupware.erp.login;

import com.groupware.erp.login.annualLeave.AnnualLeaveService;
import com.groupware.erp.token.*;
import com.groupware.erp.login.annualLeave.AnnualLeaveCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AnnualLeaveService annualLeaveService;

    @Autowired
    private AuthenticationService authenticationService;
//    @Autowired
//    private GeneralTokenProvider generalTokenProvider;

    public LoginController(JwtTokenProvider jwtTokenProvider,
                           AnnualLeaveService annualLeaveService,
                           AuthenticationService authenticationService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.annualLeaveService = annualLeaveService;
        this.authenticationService = authenticationService;
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

        //사용자 인증 로직
        Authentication authentication = authenticationService.authenticateUser(
                loginDTO.getEmpNo(), loginDTO.getEmpPassword()
        );

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        //JWT 생성
        JwtTokenDTO jwtTokenDTO = jwtTokenProvider.generateToken(authentication);
        log.info("JWT생성: {}",jwtTokenDTO);

        // 일반토큰 생성
//        String generalToken = generalTokenProvider.GeneralToken(user.getEmpNo());
//        log.info("일반토큰도 생성했다!!!!!!!!!!!!!!!",generalToken);
//
//        // 토큰 2개를 객체 한 개로
//        CombinedTokenResponse response = new CombinedTokenResponse(generalToken, jwtTokenDTO);
//
//        log.info("토큰 만들었냐 잘 봐라!!!!" , response);

        return ResponseEntity.ok(jwtTokenDTO);
    }


    @GetMapping("/changePassword")
    public String changePasswordPage(Model model) {

        log.info("비밀번호 바꾸셈");
        log.info("Model" + model.toString());
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
        log.info("이거슨 changePasswordDTO.getEmpNo 임니다 {}",changePasswordDTO.getEmpNo());
        log.info("이거슨 changePasswordDTO.getOldPassword 임니다 {}",changePasswordDTO.getOldPassword());
        log.info("이거슨 changePasswordDTO.getNewPassword 임니다 {}",changePasswordDTO.getNewPassword());


        if (isChanged) {
            log.info("비밀번호 변경 : empNo={}", changePasswordDTO.getEmpNo());
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            log.info("비밀번호 변경 실패 : 이미 사용된 비밀번호.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않거나 이미 사용된 비밀번호");
        }
    }
}
