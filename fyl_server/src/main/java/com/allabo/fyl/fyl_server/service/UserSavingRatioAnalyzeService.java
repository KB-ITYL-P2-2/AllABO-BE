package com.allabo.fyl.fyl_server.service;
import com.allabo.fyl.fyl_server.dao.UserSavingRatioAnalyzeDAO;

public interface UserSavingRatioAnalyzeService {
    void saveUserRatioSavingAnalyze(UserSavingRatioAnalyzeDAO dao);
    UserSavingRatioAnalyzeDAO findById(String id);
    void updateUserSavingRatioAnalyze(UserSavingRatioAnalyzeDAO dao);
}
