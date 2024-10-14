package com.groupware.erp.token;

import com.groupware.erp.login.LoginEntity;
import com.groupware.erp.login.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneralTokenProvider {

    @Autowired
    private LoginRepository loginRepository;

    public String GeneralToken (String empNo) {
        // DB에서 조회
        LoginEntity loginEntity = loginRepository.findByEmpNo(empNo);

        if (loginEntity == null) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        // 사용자정보 조합
        String empName = loginEntity.getEmpName();
        String empEmail = loginEntity.getEmpEmail();
        String department = loginEntity.getDepartment();
        String empGrade = loginEntity.getEmpGrade();

        // 사용자정보 조합해서 토큰 생성(사번 - 이름 - 부서 - 직급 - 이메일)
        String token = empNo + "-" + empName + "-" + department + "-" + empGrade + "-" + empEmail;

        return token;
    }
}
