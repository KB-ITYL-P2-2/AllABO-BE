package com.allabo.fyl.kb_server.assets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalUserAssetDTO {
    private int customerId;
    private int totalAccountBalance;
    private int totalCardAmount;
    private int totalInsurancePremium;
    private int totalRemainingBalance;
}
