package com.allabo.fyl.fyl_server.repository;
import com.allabo.fyl.fyl_server.dao.UserIncomeAnalyzeDAO;
import com.allabo.fyl.fyl_server.dao.UserLoanAnalyzeDAO;
import com.allabo.fyl.fyl_server.mapper.UserIncomeAnalyzeMapper;
import com.allabo.fyl.fyl_server.mapper.UserLoanAnalyzeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserLoanAnalyzeRepository {
    private final UserLoanAnalyzeMapper userLoanAnalyzeMapper;

    public void saveUserLoanAnalyze(UserLoanAnalyzeDAO dao) {
        userLoanAnalyzeMapper.saveUserLoanAnalyze(dao);
    }
    public UserLoanAnalyzeDAO findById(String id) {
        return userLoanAnalyzeMapper.findById(id);
    }
    public void updateUserIncomeAnalyze(UserLoanAnalyzeDAO dao){
        userLoanAnalyzeMapper.updateUserLoanAnalyze(dao);
    }
}
