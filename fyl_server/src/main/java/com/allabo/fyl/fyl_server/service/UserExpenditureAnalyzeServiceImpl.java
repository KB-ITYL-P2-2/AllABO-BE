package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dao.UserExpenditureAnalyzeDAO;
import com.allabo.fyl.fyl_server.repository.UserExpenditureAnalyzeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserExpenditureAnalyzeServiceImpl implements UserExpenditureAnalyzeService {
    private final UserExpenditureAnalyzeRepository userExpenditureAnalyzeRepository;

    @Override
    public void saveUserExpenditureAnalyze(UserExpenditureAnalyzeDAO dao) {
        if (dao == null || dao.getId() == null){
            log.error("Invalid UserFinancialsDTO data: {}", dao);
            throw new IllegalArgumentException("Invalid UserFinancialsDTO data");
        }
        userExpenditureAnalyzeRepository.saveUserExpenditureAnalyze(dao);
    }

    @Override
    public UserExpenditureAnalyzeDAO findById(String id) {
        UserExpenditureAnalyzeDAO dao = userExpenditureAnalyzeRepository.findById(id);
        return dao;
    }

    @Override
    public void updateUserExpenditureAnalyze(UserExpenditureAnalyzeDAO dao) {
        if(dao == null || dao.getId() == null){
            throw new RuntimeException("Invalid UserFinancialsDTO data");
        }
        if(userExpenditureAnalyzeRepository.findById(dao.getId()) != null){
            userExpenditureAnalyzeRepository.updateUserExpenditureAnalyze(dao);
        }
    }
}
