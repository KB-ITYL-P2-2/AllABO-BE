package com.allabo.fyl.fyl_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPortfolioDTO {
    private String id;
    private int totalIncome;
    private int totalAssets;
    private int totalSavings;
    private int totalInvestment;
    private int monthExpenses;
    private int totalLoan;
}
