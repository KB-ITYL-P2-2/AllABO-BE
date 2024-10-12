package com.allabo.fyl.fyl_server.mapper;
import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dao.UserSavingAnalyzeDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserSavingAnalyzeMapper {
    void saveUserSavingAnalyze(UserSavingAnalyzeDAO dao);
    UserSavingAnalyzeDAO findById(String id);
    void updateUserSavingAnalyze(UserSavingAnalyzeDAO dao);
}
