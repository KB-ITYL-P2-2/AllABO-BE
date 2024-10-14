package com.allabo.fyl.fyl_server.service.impl;

import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.mapper.UserFinancialsMapper;
import com.allabo.fyl.fyl_server.service.UserFinancialsPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFinancialsPlanServiceImpl implements UserFinancialsPlanService {

    private String financePlanResult; // 자산 설계 결과 저장
    private final UserFinancialsMapper userFinancialsMapper;

    @Autowired
    public UserFinancialsPlanServiceImpl(UserFinancialsMapper userFinancialsMapper) {
        this.userFinancialsMapper = userFinancialsMapper;
    }

    // 사용자 금융 데이터를 조회하는 메서드
    @Override
    public UserFinancialsDTO FindUserFinancials(String username) {
        // 실제 데이터베이스에서 사용자 정보를 조회
        UserFinancialsDTO dto = userFinancialsMapper.findById(username);
        System.out.println("여긴 정보 조회얌~~~~~~~~~~~~~~~~~~~~~~: " + dto);
        return dto;
    }

    // 사용자 데이터를 바탕으로 자산 분석을 위한 계산 로직
    public void calculateFinanceRatios(UserFinancialsDTO dto) {
        double monthlyIncome = dto.getMonthlyIncome();
        double totalAssets = dto.getTotalAssets();
        double totalSavings = dto.getTotalSavings();
        double totalInvestments = dto.getTotalInvestment();
        double totalExpenditure = dto.getTotalExpenses();
        double totalDebt = dto.getTotalLoan();

        double annualIncome = monthlyIncome * 12;

        // 1. 자산 대비 투자 비율(%)
        double investmentToAssetRatio = (totalInvestments / totalAssets) * 100;

        // 2. 연 소득 대비 투자 비율(%)
        double investmentToIncomeRatio = (totalInvestments / annualIncome) * 100;

        // 3. 연 소득 대비 저축 비율(%)
        double savingsToIncomeRatio = (totalSavings / annualIncome) * 100;

        // 4. 연 소득 대비 부채 비율(%)
        double debtToIncomeRatio = (totalDebt / annualIncome) * 100;

        // 5. 자산 대비 저축 비율(%)
        double savingsToAssetRatio = (totalSavings / totalAssets) * 100;

        // 6. 자산 대비 부채 비율(%)
        double debtToAssetRatio = (totalDebt / totalAssets) * 100;

        // 7. 월 소득 대비 지출 비율(%)
        double expenditureToIncomeRatio = (totalExpenditure / monthlyIncome) * 100;

        // 결과를 JSON 형식으로 저장
        financePlanResult = String.format(
                "{\"자산 대비 투자 비율(%%)\": %.2f, \"연 소득 대비 투자 비율(%%)\": %.2f, \"연 소득 대비 저축 비율(%%)\": %.2f, \"연 소득 대비 부채 비율(%%)\": %.2f, \"자산 대비 저축 비율(%%)\": %.2f, \"자산 대비 부채 비율(%%)\": %.2f, \"월 소득 대비 지출 비율(%%)\": %.2f}",
                investmentToAssetRatio, investmentToIncomeRatio, savingsToIncomeRatio, debtToIncomeRatio, savingsToAssetRatio, debtToAssetRatio, expenditureToIncomeRatio
        );
    }

    // 계산된 자산 설계 결과를 반환
    public String getFinancePlanResult() {
        return financePlanResult != null ? financePlanResult : "{\"message\": \"계산 결과가 없습니다.\"}";
    }

    // 사용자 정보를 바탕으로 자산 설계 계산 호출 메서드
    public void calculateFinancePlan(UserFinancialsDTO userFinancialsDTO) {
        calculateFinanceRatios(userFinancialsDTO); // 자산 분석을 위한 계산 로직 호출
    }
}
