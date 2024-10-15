package com.allabo.fyl.fyl_server.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialsPlanDAO {
    private String id;  // 사용자 ID

    // 지출, 저축, 투자, 부채 관련 필드
    private String spendingAdjustment;          // 지출 조정
    private int targetSavingsRate;              // 저축 목표 비율
    private String recommendedSavingsProducts;  // 추천 저축 상품
    private int targetInvestmentRatio;          // 투자 목표 비율
    private String recommendedInvestmentProducts; // 추천 투자 상품
    private String debtPriority;                // 부채 우선순위
    private String debtRepaymentPlan;           // 부채 상환 계획
    private String interestRateAdjustment;      // 금리 재조정
    private String additionalRepaymentStrategy; // 추가 상환 전략
}
