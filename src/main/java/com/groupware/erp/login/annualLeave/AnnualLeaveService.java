package com.groupware.erp.login.annualLeave;

import java.time.LocalDate;
import java.util.Optional;

public interface AnnualLeaveService {

    void updateTotalAnn(String empNo, LocalDate hireDate, int useAnn, int pendingAnn);

    Optional<AnnualLeaveEntity> findByEmpNo(String empNo);
}
