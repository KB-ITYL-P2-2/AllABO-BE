package com.allabo.fyl.kb_server.assets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalUserAssetDTO {
    private int customerId;
    private String identityNumber;
    private int totalAccountBalance;
    private int monthCardAmount;
    private int monthInsurancePremium;
    private int totalRemainingLoan;
}
