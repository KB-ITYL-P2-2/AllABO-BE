package com.allabo.fyl.fyl_server.service;
import com.allabo.fyl.fyl_server.dao.UserExpenditureAnalyzeDAO;

public interface UserExpenditureAnalyzeService {
    void saveUserExpenditureAnalyze(UserExpenditureAnalyzeDAO dao);
    UserExpenditureAnalyzeDAO findById(String id);
    void updateUserExpenditureAnalyze(UserExpenditureAnalyzeDAO dao);
}
