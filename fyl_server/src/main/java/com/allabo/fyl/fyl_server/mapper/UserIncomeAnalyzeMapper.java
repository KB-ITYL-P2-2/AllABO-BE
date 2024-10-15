package com.allabo.fyl.fyl_server.mapper;
import com.allabo.fyl.fyl_server.dao.UserIncomeAnalyzeDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserIncomeAnalyzeMapper {
    void saveUserIncomeAnalyze(UserIncomeAnalyzeDAO dao);
    UserIncomeAnalyzeDAO findById(String id);
    void updateUserIncomeAnalyze(UserIncomeAnalyzeDAO dao);
}
