package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dao.UserSavingAnalyzeDAO;
import com.allabo.fyl.fyl_server.dao.UserSavingRatioAnalyzeDAO;
import com.allabo.fyl.fyl_server.mapper.UserSavingRatioAnalyzeMapper;
import com.allabo.fyl.fyl_server.repository.UserSavingAnalyzeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSavingRatioAnalyzeServiceImpl implements UserSavingRatioAnalyzeService {
    private final UserSavingRatioAnalyzeMapper userSavingRatioAnalyzeMapper;

    @Override
    public void saveUserRatioSavingAnalyze(UserSavingRatioAnalyzeDAO dao) {
        if (dao == null || dao.getId() == null){
            log.error("Invalid UserFinancialsDTO data: {}", dao);
            throw new IllegalArgumentException("Invalid UserFinancialsDTO data");
        }

        userSavingRatioAnalyzeMapper.saveUserSavingRatioAnalyze(dao);
    }

    @Override
    public UserSavingRatioAnalyzeDAO findById(String id) {
        UserSavingRatioAnalyzeDAO dao = userSavingRatioAnalyzeMapper.findById(id);
        return dao;
    }

    @Override
    public void updateUserSavingRatioAnalyze(UserSavingRatioAnalyzeDAO dao) {
        if(dao == null || dao.getId() == null){
            throw new RuntimeException("Invalid UserFinancialsDTO data");
        }
        if(userSavingRatioAnalyzeMapper.findById(dao.getId()) != null){
            userSavingRatioAnalyzeMapper.updateUserSavingRatioAnalyze(dao);
        }

    }
}
