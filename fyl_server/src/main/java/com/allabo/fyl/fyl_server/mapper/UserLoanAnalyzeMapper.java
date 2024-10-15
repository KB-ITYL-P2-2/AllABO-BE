package com.allabo.fyl.fyl_server.mapper;
import com.allabo.fyl.fyl_server.dao.UserIncomeAnalyzeDAO;
import com.allabo.fyl.fyl_server.dao.UserLoanAnalyzeDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoanAnalyzeMapper {
    void saveUserLoanAnalyze(UserLoanAnalyzeDAO dao);
    UserLoanAnalyzeDAO findById(String id);
    void updateUserLoanAnalyze(UserLoanAnalyzeDAO dao);
}
