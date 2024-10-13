package com.allabo.fyl.fyl_server.service;
import com.allabo.fyl.fyl_server.dao.UserLoanAnalyzeDAO;

public interface UserLoanAnalyzeService {
    void saveUserLoanAnalyze(UserLoanAnalyzeDAO dao);
    UserLoanAnalyzeDAO findById(String id);
    void updateUserLoanAnalyze(UserLoanAnalyzeDAO dao);
}
