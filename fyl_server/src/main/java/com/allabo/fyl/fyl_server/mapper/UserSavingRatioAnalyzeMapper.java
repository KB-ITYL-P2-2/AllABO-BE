package com.allabo.fyl.fyl_server.mapper;
import com.allabo.fyl.fyl_server.dao.UserSavingAnalyzeDAO;
import com.allabo.fyl.fyl_server.dao.UserSavingRatioAnalyzeDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserSavingRatioAnalyzeMapper {
    void saveUserSavingRatioAnalyze(UserSavingRatioAnalyzeDAO dao);
    UserSavingRatioAnalyzeDAO findById(String id);
    void updateUserSavingRatioAnalyze(UserSavingRatioAnalyzeDAO dao);
}
