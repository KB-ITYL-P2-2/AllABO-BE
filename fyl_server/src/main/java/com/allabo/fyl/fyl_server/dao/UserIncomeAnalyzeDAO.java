package com.allabo.fyl.fyl_server.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIncomeAnalyzeDAO {
    private String id;
    private String userIncomeLevel; // 사용자 소득 분위
    private String incomeComparisonAnalysis; // 소득 비교 분석
    private String incomeExpenseRatioDifference; // 소득 및 지출 비율 차이
    private String expenseManagementNeed; // 지출 관리 필요성
    private String overallAnalysis; // 종합 분석
}
