package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dao.UserSavingAnalyzeDAO;
import com.allabo.fyl.fyl_server.repository.UserFinancialsRatioRepository;
import com.allabo.fyl.fyl_server.repository.UserSavingAnalyzeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSavingAnalyzeServiceImpl implements UserSavingAnalyzeService {
    private final UserSavingAnalyzeRepository userSavingAnalyzeRepository;
    @Override
    public void saveUserSavingAnalyze(UserSavingAnalyzeDAO dao) {
        if (dao == null || dao.getId() == null){
            log.error("Invalid UserFinancialsDTO data: {}", dao);
            throw new IllegalArgumentException("Invalid UserFinancialsDTO data");
        }

        userSavingAnalyzeRepository.saveUserSavingAnalyze(dao);
    }

    @Override
    public UserSavingAnalyzeDAO findById(String id) {
        UserSavingAnalyzeDAO dao = userSavingAnalyzeRepository.findById(id);
        return dao;
    }

    @Override
    public void updateUserSavingAnalyze(UserSavingAnalyzeDAO dao) {
        if(dao == null || dao.getId() == null){
            throw new RuntimeException("Invalid UserFinancialsDTO data");
        }
        if(userSavingAnalyzeRepository.findById(dao.getId()) != null){
            userSavingAnalyzeRepository.updateUserSavingAnalyze(dao);
        }
    }

}
