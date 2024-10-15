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
public class UserFinancialsValueRepository {
    private final UserFinancialsMapper userFinancialsMapper;

    @Transactional
    public void saveUserFinancial(UserFinancialsDTO userFinancialDTO) {
        try {
            userFinancialsMapper.insertUserFinancial(userFinancialDTO);
            log.info("User financial data saved successfully for user: {}", userFinancialDTO.getId());
        } catch (DataAccessException e) {
            log.error("Error saving user financial data for user: {}", userFinancialDTO.getId(), e);
            throw new RuntimeException("Error saving user financial data", e);
        }
    }

    public Optional<UserFinancialsDTO> findUserFinancial(String id) {
        try {
            UserFinancialsDTO dto = userFinancialsMapper.findById(id);
            log.info("User financial data retrieved for user: {}", id);
            return Optional.ofNullable(dto);
        } catch (DataAccessException e) {
            log.error("Error retrieving user financial data for user: {}", id, e);
            return Optional.empty();
        }
    }

    @Transactional
    public void updateUserFinancial(UserFinancialsDTO userFinancialsDTO) {
        try {
            userFinancialsMapper.updateUserFinancial(userFinancialsDTO);
            log.info("User financial data updated successfully for user: {}", userFinancialsDTO.getId());
        } catch (DataAccessException e) {
            log.error("Error updating user financial data for user: {}", userFinancialsDTO.getId(), e);
            throw new RuntimeException("Error updating user financial data", e);
        }
    }

    @Transactional
    public void saveUserFinancialsRatio(UserFinancialsRatioDAO dao) {
        try {
            userFinancialsMapper.saveUserFinancialsRatio(dao);
            log.info("User financials ratio saved successfully for user: {}", dao.getId());
        } catch (DataAccessException e) {
            log.error("Error saving user financials ratio for user: {}", dao.getId(), e);
            throw new RuntimeException("Error saving user financials ratio", e);
        }
    }
}