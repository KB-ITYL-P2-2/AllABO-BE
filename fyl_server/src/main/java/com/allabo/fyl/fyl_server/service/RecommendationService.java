package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dto.Recommendation;
import com.allabo.fyl.fyl_server.mapper.RecommendationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    private RecommendationMapper recommendationMapper;

    // 카드 종류에 따라 맞춤 상품 추천 로직
    public List<Recommendation> getRecommendations(String cardType, Integer feeOption, String benefits, String category) {
        if (cardType.equals("신용카드")) {
            Integer[] feeRange = getAnnualFeeRange(feeOption);
            Integer annualFeeMin = feeRange[0];
            Integer annualFeeMax = feeRange[1];
            return recommendationMapper.selectCreditCardRecommendations(annualFeeMin, annualFeeMax, benefits, category);
        } else if (cardType.equals("체크카드")) {
            return recommendationMapper.selectCheckCardRecommendations(benefits, category);
        } else {
            return null; // 잘못된 카드 타입일 경우 null 리턴
        }
    }

    // 연회비 범위를 설정하는 메서드
    // getAnnualFeeRange() 메서드에서 null일 경우에 대한 기본 처리 추가
    private Integer[] getAnnualFeeRange(Integer feeOption) {
        if (feeOption == null) return new Integer[] {0, Integer.MAX_VALUE};  // 기본 범위 설정
        switch (feeOption) {
            case 0: return new Integer[] { 0, 0 };          // 없음
            case 1: return new Integer[] { 0, 10000 };      // 1만원 이하
            case 2: return new Integer[] { 10000, 30000 };  // 1만원 ~ 3만원
            case 3: return new Integer[] { 30000, 100000 }; // 3만원 ~ 10만원
            case 4: return new Integer[] { 100000, Integer.MAX_VALUE }; // 10만원 이상
            default: return new Integer[] { 0, Integer.MAX_VALUE };  // 기본 범위 설정
        }
    }

}