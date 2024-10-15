package com.allabo.fyl.fyl_server.service;
import com.allabo.fyl.fyl_server.dao.UserSavingAnalyzeDAO;

public interface UserSavingAnalyzeService {
    void saveUserSavingAnalyze(UserSavingAnalyzeDAO dao);
    UserSavingAnalyzeDAO findById(String id);
    void updateUserSavingAnalyze(UserSavingAnalyzeDAO dao);
}
