package com.allabo.fyl.fyl_server.repository;
import com.allabo.fyl.fyl_server.dao.UserSavingRatioAnalyzeDAO;
import com.allabo.fyl.fyl_server.mapper.UserSavingRatioAnalyzeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserSavingRatioAnalyzeRepository {
    private final UserSavingRatioAnalyzeMapper userSavingRatioAnalyzeMapper;

    public void saveUserSavingRatioAnalyze(UserSavingRatioAnalyzeDAO dao) {
        userSavingRatioAnalyzeMapper.saveUserSavingRatioAnalyze(dao);
    }
    public UserSavingRatioAnalyzeDAO findById(String id) {
        return userSavingRatioAnalyzeMapper.findById(id);
    }
    public void updateUserSavingRatioAnalyze(UserSavingRatioAnalyzeDAO dao){
        userSavingRatioAnalyzeMapper.updateUserSavingRatioAnalyze(dao);
    }

}
