package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dao.UserIncomeAnalyzeDAO;
import com.allabo.fyl.fyl_server.repository.UserIncomeAnalyzeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserIncomeAnalyzeServiceImpl implements UserIncomeAnalyzeService {
    private final UserIncomeAnalyzeRepository userIncomeAnalyzeRepository;

    @Override
    public void saveUserIncomeAnalyze(UserIncomeAnalyzeDAO dao) {
        if (dao == null || dao.getId() == null){
            log.error("Invalid UserFinancialsDTO data: {}", dao);
            throw new IllegalArgumentException("Invalid UserFinancialsDTO data");
        }
        userIncomeAnalyzeRepository.saveUserIncomeAnalyze(dao);
    }

    @Override
    public UserIncomeAnalyzeDAO findById(String id) {
        UserIncomeAnalyzeDAO dao = userIncomeAnalyzeRepository.findById(id);
        return dao;
    }

    @Override
    public void updateUserIncomeAnalyze(UserIncomeAnalyzeDAO dao) {
        if(dao == null || dao.getId() == null){
            throw new RuntimeException("Invalid UserFinancialsDTO data");
        }
        if(userIncomeAnalyzeRepository.findById(dao.getId()) != null){
            userIncomeAnalyzeRepository.updateUserIncomeAnalyze(dao);
        }
    }

}
