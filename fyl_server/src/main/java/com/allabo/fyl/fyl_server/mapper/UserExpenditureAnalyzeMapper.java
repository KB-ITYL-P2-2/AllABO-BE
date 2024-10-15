package com.allabo.fyl.fyl_server.mapper;
import com.allabo.fyl.fyl_server.dao.UserExpenditureAnalyzeDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserExpenditureAnalyzeMapper {
    void saveUserExpenditureAnalyze(UserExpenditureAnalyzeDAO dao);
    UserExpenditureAnalyzeDAO findById(String id);
    void updateUserExpenditureAnalyze(UserExpenditureAnalyzeDAO dao);
}
