package com.allabo.fyl.fyl_server.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFinancialsRatioDAO {
    private String id;
    private int assetInvestmentRatio; // 자산 대비 투자 비율
    private int incomeInvestmentRatio; // 연 소득 대비 투자 비율
    private int incomeSavingsRatio; // 연 소득 대비 저축 비율
    private int incomeDebtRatio; // 연 소득 대비 부채 비율
    private int assetSavingsRatio; // 자산 대비 저축 비율
    private int assetDebtRatio; // 자산 대비 부채 비율
    private int incomeExpenditureRatio; // 연 소득 대비 지출 비율
}
