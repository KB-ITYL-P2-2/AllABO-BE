package com.allabo.fyl.fyl_server.repository;

import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.mapper.UserFinancialsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserFinancialsRepository {
    private final UserFinancialsMapper userFinancialsMapper;
    public void saveUserFinancial(UserFinancialsDTO userFinancialDTO) {
        userFinancialsMapper.insertUserFinancial(userFinancialDTO);
    }

    public UserFinancialsDTO findUserFinancial(String id) {
        return userFinancialsMapper.findById(id);
    }

    public void updateUserFinancial(UserFinancialsDTO userFinancialsDTO) {
        userFinancialsMapper.updateUserFinancial(userFinancialsDTO);
    }

    public void saveUserFinancialsRatio(UserFinancialsRatioDAO dao) {
        userFinancialsMapper.saveUserFinancialsRatio(dao);
    }

}