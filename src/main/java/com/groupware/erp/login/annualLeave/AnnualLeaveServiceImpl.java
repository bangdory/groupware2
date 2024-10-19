package com.groupware.erp.login.annualLeave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AnnualLeaveServiceImpl implements AnnualLeaveService{

    @Autowired
    private AnnualLeaveRepository annualLeaveRepository;

    @Override
    public void updateTotalAnn(String empNo, LocalDate hireDate, int useAnn, int pendingAnn) {
        Optional<AnnualLeaveEntity> oal = annualLeaveRepository.findByEmpNo(empNo);
        LocalDate today = LocalDate.now();
        if (oal.isPresent()) {
            AnnualLeaveCalculator alc = new AnnualLeaveCalculator();

            // 총 연차 수 계산
            int totalAnn = alc.calculateAnnualLeave(hireDate);

            // 남은 연차 수 계산
            int remAnn = alc.calculateRemainingAnn(totalAnn, useAnn);

            // 입사일의 월/일이 오늘과 일치하는지 확인
            if (today.getMonth() == hireDate.getMonth() && today.getDayOfMonth() == hireDate.getDayOfMonth()) {
                // 입사일과 일치하면 연차 초기화 로직 실행
                pendingAnn += remAnn;  // 미처리 연차에 남은 연차 누적
                useAnn = 0;            // 사용 연차 초기화
                remAnn = totalAnn;
            }

            AnnualLeaveEntity ale = oal.get();

            ale.setTotalAnn(totalAnn); // 총 연차 수 설정
            ale.setRemAnn(remAnn); // 남은 연차 수 계산
            ale.setUseAnn(useAnn);                // 사용 연차 설정
            ale.setPendingAnn(pendingAnn);        // 미처리 연차 설정

            annualLeaveRepository.save(ale);

//
//            // 총 연차 수 계산
//            int totalAnn = alc.calculateAnnualLeave(hireDate);
//
//            // 잔여 연차 수 계산 및 매년 사용 연차 초기화
//            int remAnn = alc.calculateRemainingAnn(hireDate, totalAnn, useAnn);
//
//            // 미처리된 잔여 연차 누적
//            int processPendingAnn = alc.calculatePendingAnn(hireDate,totalAnn,useAnn,pendingAnn);
//
//            // Entity 객체 가져오기
//            AnnualLeaveEntity ale = oal.get();
//
//            ale.setTotalAnn(totalAnn);
//            ale.setRemAnn(remAnn);
//            ale.setPendingAnn(processPendingAnn);
//
//            // 입사일의 월/일이 오늘의 월/일과 일치하는지 확인
//            if (today.getMonth() == hireDate.getMonth() && today.getDayOfMonth() == hireDate.getDayOfMonth()) {
//                // 사용한 연차를 0으로 초기화 (입사일 기준으로 매년 초기화)
//                useAnn = 0;
//            }
//
//            ale.setUseAnn(useAnn);
//
//            annualLeaveRepository.save(ale);

        }else {
            System.out.println("AnnualLeaveService - updateTotalAnn - Error 발생 (데이터 없음)");
        }
    }

    @Override
    public Optional<AnnualLeaveEntity> findByEmpNo(String empNo) {
        return annualLeaveRepository.findByEmpNo(empNo);
    }

}
