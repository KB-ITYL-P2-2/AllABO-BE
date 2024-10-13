package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dao.UserIncomeAnalyzeDAO;
import com.allabo.fyl.fyl_server.dao.UserLoanAnalyzeDAO;
import com.allabo.fyl.fyl_server.repository.UserIncomeAnalyzeRepository;
import com.allabo.fyl.fyl_server.repository.UserLoanAnalyzeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLoanAnalyzeServiceImpl implements UserLoanAnalyzeService {
    private final UserLoanAnalyzeRepository userLoanAnalyzeRepository;

    @Override
    public void saveUserLoanAnalyze(UserLoanAnalyzeDAO dao) {
        if (dao == null || dao.getId() == null){
            log.error("Invalid UserFinancialsDTO data: {}", dao);
            throw new IllegalArgumentException("Invalid UserFinancialsDTO data");
        }
        userLoanAnalyzeRepository.saveUserLoanAnalyze(dao);
    }

    @Override
    public UserLoanAnalyzeDAO findById(String id) {
        UserLoanAnalyzeDAO dao = userLoanAnalyzeRepository.findById(id);
        return dao;
    }

    @Override
    public void updateUserLoanAnalyze(UserLoanAnalyzeDAO dao) {
        if(dao == null || dao.getId() == null){
            throw new RuntimeException("Invalid UserFinancialsDTO data");
        }
        if(userLoanAnalyzeRepository.findById(dao.getId()) != null){
            userLoanAnalyzeRepository.updateUserIncomeAnalyze(dao);
        }
    }
}
