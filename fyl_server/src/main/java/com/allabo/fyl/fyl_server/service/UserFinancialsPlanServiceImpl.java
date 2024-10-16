package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dao.FinancialsPlanDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsValueDTO;
import com.allabo.fyl.fyl_server.repository.UserFinancialsPlanRepository;
import com.allabo.fyl.fyl_server.repository.UserFinancialsValueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFinancialsPlanServiceImpl implements UserFinancialsPlanService {

    private final UserFinancialsValueRepository userFinancialsValueRepository;
    private final UserFinancialsPlanRepository userFinancialsPlanRepository;

    @Override
    public Optional<UserFinancialsValueDTO> findUserFinancials(String username) {
        log.info("Attempting to find user financials for username: {}", username);
        return userFinancialsValueRepository.findUserFinancial(username);
    }

    @Override
    public void saveOrUpdateFinancialPlan(FinancialsPlanDAO financialsPlanDAO) {
        log.info("Attempting to save or update financial plan for ID: {}", financialsPlanDAO.getId());
        userFinancialsPlanRepository.saveFinancialPlan(financialsPlanDAO);
        log.info("Financial plan saved/updated for ID: {}", financialsPlanDAO.getId());
    }

    @Override
    public Optional<FinancialsPlanDAO> getFinancialPlan(String userId) {
        log.info("Attempting to get financial plan for user ID: {}", userId);
        Optional plans = userFinancialsPlanRepository.getFinancialPlan(userId);
        System.out.println("@@@@@@" + plans.toString());
        return plans;
    }

    // 만약 인터페이스에 saveFinancialPlan 메서드가 별도로 있다면 아래와 같이 구현합니다:
    /*
    @Override
    public void saveFinancialPlan(FinancialsPlanDAO financialsPlanDAO) {
        log.info("Saving financial plan for ID: {}", financialsPlanDAO.getId());
        userFinancialsPlanRepository.saveFinancialPlan(financialsPlanDAO);
    }
    */
}