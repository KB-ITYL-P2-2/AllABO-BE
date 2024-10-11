package com.allabo.fyl.fyl_server.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDAO {
    private int creditCardId;
    private String productId;
    private String creditCardName;
    private String creditCardContent;
    private String creditCardDetailUrl;
    private String creditCardImageUrl;
    private String creditCardCategory;
    private int creditCardAnnualFee;
    private String creditCardKeyword;
}
