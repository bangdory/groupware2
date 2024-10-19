package com.groupware.erp.login.annualLeave;

import java.time.LocalDate;
import java.time.Period;

public class AnnualLeaveCalculator {

    private AnnualLeaveService annualLeaveService;

    // 입사일을 기준으로 연차 계산
    public int calculateAnnualLeave(LocalDate hireDate) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(hireDate, today);
        int yearsWorked = period.getYears(); // 근속 연수 계산

        if (yearsWorked < 1) {
            // 1년 미만이면 매달 1일씩, 최대 11일
            int monthsWorked = period.getMonths();
            return Math.min(11, monthsWorked); // 최대 11일
        } else {
            // 1년 이상 근속 시 기본 15일 + 2년마다 1일 추가
            int annualLeave = 15 + ((yearsWorked - 1) / 2);
            return Math.min(25, annualLeave); // 최대 25일
        }
    }

//    // 사용한 연차를 입사일을 기준으로 매년 초기화하고 남은 연차 및 미처리 연차 계산
//    public int resetUsedAnnualLeave(LocalDate hireDate, int totalAnnualLeave, int usedAnnualLeave, int pendingAnnualLeave) {
//        LocalDate today = LocalDate.now();
//
//        // 입사일의 월/일이 오늘의 월/일과 일치하는지 확인
//        if (today.getMonth() == hireDate.getMonth() && today.getDayOfMonth() == hireDate.getDayOfMonth()) {
//            // 입사일의 월/일과 오늘이 같으면 사용한 연차 초기화 전에 남은 연차 저장
//            pendingAnnualLeave += (totalAnnualLeave - usedAnnualLeave);  // 미처리 연차에 남은 연차 추가
//            usedAnnualLeave = 0;  // 사용한 연차 초기화
//        }
//
//        // 남은 연차 계산 (총 연차 - 사용한 연차)
//        int remAnn = totalAnnualLeave - usedAnnualLeave;
//
//        // DB에 남은 연차와 미처리 연차를 저장하는 로직이 필요함
////        updateAnnualLeaveInDB(remainingAnnualLeave, pendingAnnualLeave);
//
//        return remAnn;
//    }

//    // 미처리 연차를 계산하는 메서드
//    public int calculatePendingAnn(LocalDate hireDate, int totalAnnualLeave, int usedAnnualLeave, int pendingAnnualLeave) {
//        LocalDate today = LocalDate.now();
//
//        // 입사일의 월/일이 오늘의 월/일과 일치하는지 확인
//        if (today.getMonth() == hireDate.getMonth() && today.getDayOfMonth() == hireDate.getDayOfMonth()) {
//            // 현재 남은 연차 계산
//            int remainingAnnualLeave = totalAnnualLeave - usedAnnualLeave;
//
//            // 기존 미처리 연차에 남은 연차를 더해서 누적
//            pendingAnnualLeave += remainingAnnualLeave;
//
//            return pendingAnnualLeave;  // 누적된 미처리 연차 반환
//        }
//
//        return pendingAnnualLeave; // 변경 없을 경우 기존 미처리 연차 반환
//    }
//
//    // 남은 연차 계산 메서드 (입사일 기준으로 사용 연차 초기화 포함)
//    public int calculateRemainingAnn(LocalDate hireDate, int totalAnnualLeave, int usedAnnualLeave) {
//        LocalDate today = LocalDate.now();
//
//        // 입사일의 월/일이 오늘의 월/일과 일치하는지 확인
//        if (today.getMonth() == hireDate.getMonth() && today.getDayOfMonth() == hireDate.getDayOfMonth()) {
//            // 사용한 연차를 0으로 초기화 (입사일 기준으로 매년 초기화)
//            usedAnnualLeave = 0;
//        }
//
//        // 남은 연차 계산 (총 연차 - 사용 연차)
//        return totalAnnualLeave - usedAnnualLeave;
//    }

    // 남은 연차만 계산하는 메서드
    public int calculateRemainingAnn(int totalAnnualLeave, int usedAnnualLeave) {
        return totalAnnualLeave - usedAnnualLeave;  // 남은 연차 반환
    }

}
