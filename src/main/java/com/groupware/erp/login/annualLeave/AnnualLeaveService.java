package com.groupware.erp.login.annualLeave;

import java.time.LocalDate;

public interface AnnualLeaveService {

    void updateTotalAnn(String empNo, LocalDate hireDate, int useAnn, int pendingAnn);
}
