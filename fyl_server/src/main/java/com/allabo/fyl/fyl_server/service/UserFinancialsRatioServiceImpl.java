package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.repository.UserFinancialsRatioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFinancialsRatioServiceImpl implements UserFinancialsRatioService {
    private final UserFinancialsRatioRepository userFinancialsRatioRepository;
    @Override
    public void saveUserFinancialsRatio(UserFinancialsRatioDAO dao) {
        if (dao == null || dao.getId() == null){
            log.error("Invalid UserFinancialsDTO data: {}", dao);
            throw new IllegalArgumentException("Invalid UserFinancialsDTO data");
        }

        userFinancialsRatioRepository.saveUserFinancialsRatio(dao);
    }

    @Override
    public UserFinancialsRatioDAO findById(String id) {
        UserFinancialsRatioDAO dao = userFinancialsRatioRepository.findById(id);
//        if (dao == null || id == null){
//            throw new IllegalArgumentException("id가 null이거나 존재하는 financial 데이터가 없습니다.");
//        }
        return dao;
    }

    @Override
    public void updateUserFinancialRatio(UserFinancialsRatioDAO dao) {
        if(dao == null || dao.getId() == null){
            throw new RuntimeException("Invalid UserFinancialsDTO data");
        }
        if(userFinancialsRatioRepository.findById(dao.getId()) != null){
            userFinancialsRatioRepository.updateUserFinancialRatio(dao);
        }
    }


}
