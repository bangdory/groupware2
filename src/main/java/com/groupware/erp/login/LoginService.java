package com.groupware.erp.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(LoginRepository loginRepository, PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginEntity findByEmpNo(String empNo) {
        return loginRepository.findByEmpNo(empNo);
    }

    public boolean updatePassword(String empNo, String oldPassword, String newPassword) {

        // DB에서 사용자 정보 가져오기
        LoginEntity user = loginRepository.findByEmpNo(empNo);

        log.info("user를 제대로 가지고 왔나요?!? {}", user);

        if (user != null) {
            // 비밀번호 인코딩여부 확인
            boolean isEmpPasswordEncoded = isPasswordEncoded(user.getEmpPassword());

            boolean isOldPasswordValid = isEmpPasswordEncoded
                    ? passwordEncoder.matches(oldPassword, user.getEmpPassword())
                    : oldPassword.equals(user.getEmpPassword());

            if(isOldPasswordValid) {

                boolean isNewPasswordValid = isEmpPasswordEncoded
                        ? passwordEncoder.matches(newPassword, user.getEmpPassword())
                        : newPassword.equals(user.getEmpPassword());

                if (isNewPasswordValid) {
                        log.info("같은비밀번호");
                        return false;
                }

                // 새 비밀번호를 BCrypt로 인코딩
                String encodedPassword = passwordEncoder.encode(newPassword);
                user.setEmpPassword(encodedPassword);
                loginRepository.save(user); // 변경사항 저장
                return true;
            }
            log.info("비밀번호가!!!!! 틀렸다구요!!!!!!!!!");
            log.info("사용자 정보에서 user.getEmpPassword는 제대론가요!??!{}", user.getEmpPassword());
            return false;


        }
        return false;
    }

    private boolean isPasswordEncoded(String password) {
        return password != null && password.length() >= 60 && password.startsWith("$2a$");
    }
}
