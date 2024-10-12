package com.allabo.fyl.fyl_server.repository;
import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dao.UserSavingAnalyzeDAO;
import com.allabo.fyl.fyl_server.mapper.UserFinancialsRatioMapper;
import com.allabo.fyl.fyl_server.mapper.UserSavingAnalyzeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserSavingAnalyzeRepository {
    private final UserSavingAnalyzeMapper userSavingAnalyzeMapper;

    public void saveUserSavingAnalyze(UserSavingAnalyzeDAO dao) {
        userSavingAnalyzeMapper.saveUserSavingAnalyze(dao);
    }
    public UserSavingAnalyzeDAO findById(String id) {
        return userSavingAnalyzeMapper.findById(id);
    }
    public void updateUserSavingAnalyze(UserSavingAnalyzeDAO dao){
        userSavingAnalyzeMapper.updateUserSavingAnalyze(dao);
    }

}
