package com.allabo.fyl.fyl_server.repository;
import com.allabo.fyl.fyl_server.dao.UserIncomeAnalyzeDAO;
import com.allabo.fyl.fyl_server.dao.UserSavingAnalyzeDAO;
import com.allabo.fyl.fyl_server.mapper.UserIncomeAnalyzeMapper;
import com.allabo.fyl.fyl_server.mapper.UserSavingAnalyzeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserIncomeAnalyzeRepository {
    private final UserIncomeAnalyzeMapper userIncomeAnalyzeMapper;

    public void saveUserIncomeAnalyze(UserIncomeAnalyzeDAO dao) {
        userIncomeAnalyzeMapper.saveUserIncomeAnalyze(dao);
    }
    public UserIncomeAnalyzeDAO findById(String id) {
        return userIncomeAnalyzeMapper.findById(id);
    }
    public void updateUserIncomeAnalyze(UserIncomeAnalyzeDAO dao){
        userIncomeAnalyzeMapper.updateUserIncomeAnalyze(dao);
    }
}
