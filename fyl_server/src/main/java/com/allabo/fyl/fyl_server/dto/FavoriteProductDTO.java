package com.allabo.fyl.fyl_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteProductDTO {
    private int favoritesId;
    private String id;
    private String productId;
    private int productNum;

    // 예적금 정보
    private int depositId;
    private String depositName;
    private String depositContent;
    private String depositCategory;
    private String depositUrl;
    private String depositInterestRate;
    private int depositMinAmount;
    private boolean depositOnline;
    private int depositDuration;
    private boolean depositPreferInterestRate;

    // 대출 정보
    private int loanId;
    private String loanName;
    private String loanUrl;
    private String loanContent;
    private String loanChannel;
    private String loanAmount;
    private String loanKeyword;
    private String loanType;

    // 보험 정보
    private int insuranceId;
    private String insuranceName;
    private String insuranceUrl;
    private String insuranceKeyword;
    private String insuranceType;

    // 체크카드 정보
    private int checkCardId;
    private String checkCardName;
    private String checkCardContent;
    private String checkCardDetailUrl;
    private String checkCardImageUrl;
    private String checkCardCategory;

    // 신용카드 정보
    private int creditCardId;
    private String creditCardName;
    private String creditCardContent;
    private String creditCardDetailUrl;
    private String creditCardImageUrl;
    private String creditCardCategory;
    private int creditCardAnnualFee;
    private String creditCardKeyword;
}
