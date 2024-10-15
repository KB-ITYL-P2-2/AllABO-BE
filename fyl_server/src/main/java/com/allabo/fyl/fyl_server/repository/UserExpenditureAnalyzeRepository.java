package com.allabo.fyl.fyl_server.repository;
import com.allabo.fyl.fyl_server.dao.UserExpenditureAnalyzeDAO;
import com.allabo.fyl.fyl_server.mapper.UserExpenditureAnalyzeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserExpenditureAnalyzeRepository {
    private final UserExpenditureAnalyzeMapper userExpenditureAnalyzeMapper;

    public void saveUserExpenditureAnalyze(UserExpenditureAnalyzeDAO dao) {
        userExpenditureAnalyzeMapper.saveUserExpenditureAnalyze(dao);
    }
    public UserExpenditureAnalyzeDAO findById(String id) {
        return userExpenditureAnalyzeMapper.findById(id);
    }
    public void updateUserExpenditureAnalyze(UserExpenditureAnalyzeDAO dao){
        userExpenditureAnalyzeMapper.updateUserExpenditureAnalyze(dao);
    }

}
