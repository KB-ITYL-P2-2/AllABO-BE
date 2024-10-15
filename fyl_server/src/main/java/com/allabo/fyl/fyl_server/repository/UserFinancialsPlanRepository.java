package com.allabo.fyl.fyl_server.repository;

import com.allabo.fyl.fyl_server.dao.FinancialsPlanDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class UserFinancialsPlanRepository {

    private final Map<String, FinancialsPlanDAO> inMemoryFinancialPlans = new HashMap<>();

    public void saveFinancialPlan(FinancialsPlanDAO plan) {
        inMemoryFinancialPlans.put(plan.getId(), plan);
        log.info("Financial plan saved for user: {}", plan.getId());
    }

    public Optional<FinancialsPlanDAO> getFinancialPlan(String userId) {
        return Optional.ofNullable(inMemoryFinancialPlans.get(userId));
    }
}