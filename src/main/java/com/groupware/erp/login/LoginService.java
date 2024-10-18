package com.groupware.erp.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

        if (user != null) {
            if (user.getEmpPassword().equals(newPassword)) {
                log.info("같은비밀번호");
                return false;
            }

            // 새 비밀번호를 BCrypt로 인코딩
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setEmpPassword(encodedPassword);
            loginRepository.save(user); // 변경사항 저장
            return true;
        }
        return false;
    }
}
