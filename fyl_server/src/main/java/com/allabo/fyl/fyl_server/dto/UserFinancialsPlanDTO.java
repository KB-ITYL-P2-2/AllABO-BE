package com.allabo.fyl.fyl_server.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserFinancialsPlanDTO {
    private String username;
    private ImprovedStrategyDTO improvedStrategy;

    @Data
    public static class ImprovedStrategyDTO {
        private String expenditureAdjustment;
        private SavingsStrategyDTO savingsStrategy;
        private InvestmentStrategyDTO investmentStrategy;
        private DebtManagementDTO debtManagement;
    }

    @Data
    public static class SavingsStrategyDTO {
        private double targetSavingsRate;
        private List<String> recommendedSavingsProducts;
    }

    @Data
    public static class InvestmentStrategyDTO {
        private double targetInvestmentRatio;
        private List<String> recommendedInvestmentProducts;
    }

    @Data
    public static class DebtManagementDTO {
        private String priority;
        private String repaymentPlan;
        private String interestRateAdjustment;
        private String additionalRepaymentStrategy;
    }
}