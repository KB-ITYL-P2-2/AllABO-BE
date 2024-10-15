package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dao.CreditCardDAO;
import com.allabo.fyl.fyl_server.dao.DepositDAO;
import com.allabo.fyl.fyl_server.mapper.RecommendationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    private RecommendationMapper recommendationMapper;

    // 카드 종류에 따라 맞춤 상품 추천 로직
    public List<CreditCardDAO> getRecommendations(String cardType, Integer feeOption, String benefits, String category) {
        if (cardType.equals("신용카드")) {
            Integer[] feeRange = getAnnualFeeRange(feeOption);
            Integer annualFeeMin = feeRange[0];
            Integer annualFeeMax = feeRange[1];
            return recommendationMapper.selectCreditCardRecommendations(annualFeeMin, annualFeeMax, benefits, category);
        } else {
            return null;
        }
    }

    // 예적금 추천 로직
    public List<DepositDAO> getDepositRecommendations(String depositType, Integer minAmount, Boolean onlineOnly, Integer duration, Boolean preferInterestRate) {
        System.out.println(depositType);
        System.out.println(minAmount);
        System.out.println(onlineOnly);
        System.out.println(duration);
        System.out.println(preferInterestRate);
        return recommendationMapper.selectDepositRecommendations(depositType,minAmount, onlineOnly, duration, preferInterestRate);
    }

    // 연회비 범위를 설정하는 메서드
    private Integer[] getAnnualFeeRange(Integer feeOption) {
        if (feeOption == null) return new Integer[]{0, Integer.MAX_VALUE};  // 기본 범위 설정
        switch (feeOption) {
            case 0: return new Integer[]{0, 0};          // 없음
            case 1: return new Integer[]{0, 10000};      // 1만원 이하
            case 2: return new Integer[]{10000, 30000};  // 1만원 ~ 3만원
            case 3: return new Integer[]{30000, 100000}; // 3만원 ~ 10만원
            case 4: return new Integer[]{100000, Integer.MAX_VALUE}; // 10만원 이상
            default: return new Integer[]{0, Integer.MAX_VALUE};  // 기본 범위 설정
        }
    }
}
