package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dao.FinancialsPlanDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsValueDTO;

import java.util.Optional;

public interface UserFinancialsPlanService {
    Optional<UserFinancialsValueDTO> findUserFinancials(String username);
    void saveOrUpdateFinancialPlan(FinancialsPlanDAO financialsPlanDAO);
    Optional<FinancialsPlanDAO> getFinancialPlan(String userId);
}