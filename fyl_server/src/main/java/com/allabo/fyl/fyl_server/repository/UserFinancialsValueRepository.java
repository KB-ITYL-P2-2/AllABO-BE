package com.allabo.fyl.fyl_server.repository;

import com.allabo.fyl.fyl_server.dto.UserFinancialsValueDTO;
import com.allabo.fyl.fyl_server.mapper.UserFinancialsMapper;
import com.allabo.fyl.fyl_server.mapper.UserFinancialsValueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserFinancialsValueRepository {
    private final UserFinancialsValueMapper userFinancialsValueMapper;

    @Transactional
    public void saveUserFinancial(UserFinancialsValueDTO userFinancialValueDTO) {
        try {
            userFinancialsValueMapper.insertUserFinancialValue(userFinancialValueDTO);
            log.info("User financial data saved successfully for user: {}", userFinancialValueDTO.getId());
        } catch (DataAccessException e) {
            log.error("Error saving user financial data for user: {}", userFinancialValueDTO.getId(), e);
            throw new RuntimeException("Error saving user financial data", e);
        }
    }

    public Optional<UserFinancialsValueDTO> findUserFinancial(String id) {
        try {
            UserFinancialsValueDTO dto = userFinancialsValueMapper.findValueById(id);
            log.info("User financial data retrieved for user: {}", id);
            return Optional.ofNullable(dto);
        } catch (DataAccessException e) {
            log.error("Error retrieving user financial data for user: {}", id, e);
            return Optional.empty();
        }
    }

    @Transactional
    public void updateUserFinancialValue(UserFinancialsValueDTO userFinancialsValueDTO) {
        try {
            userFinancialsValueMapper.updateUserFinancialValue(userFinancialsValueDTO);
            log.info("User financial data updated successfully for user: {}", userFinancialsValueDTO.getId());
        } catch (DataAccessException e) {
            log.error("Error updating user financial data for user: {}", userFinancialsValueDTO.getId(), e);
            throw new RuntimeException("Error updating user financial data", e);
        }
    }

//    @Transactional
//    public void saveUserFinancialsRatio(UserFinancialsRatioDAO dao) {
//        try {
//            userFinancialsMapper.saveUserFinancialsRatio(dao);
//            log.info("User financials ratio saved successfully for user: {}", dao.getId());
//        } catch (DataAccessException e) {
//            log.error("Error saving user financials ratio for user: {}", dao.getId(), e);
//            throw new RuntimeException("Error saving user financials ratio", e);
//        }
//    }
}