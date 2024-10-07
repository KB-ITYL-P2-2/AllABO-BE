package com.allabo.fyl.fyl_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFinancialsDTO {
    private String id;
    private String monthlyIncome;
    private String totalAssets;
    private String totalSavings;
    private String totalInvestment;
    private String totalExpenses;
    private String totalLoan;
}
