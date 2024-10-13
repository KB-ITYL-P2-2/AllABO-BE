package com.allabo.fyl.fyl_server.service;
import com.allabo.fyl.fyl_server.dao.UserIncomeAnalyzeDAO;

public interface UserIncomeAnalyzeService {
    void saveUserIncomeAnalyze(UserIncomeAnalyzeDAO dao);
    UserIncomeAnalyzeDAO findById(String id);
    void updateUserIncomeAnalyze(UserIncomeAnalyzeDAO dao);
}
