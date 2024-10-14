package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dao.CreditCardDAO;
import com.allabo.fyl.fyl_server.dto.Recommendation;
import com.allabo.fyl.fyl_server.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.allabo.fyl.fyl_server.dao.DepositDAO;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    // 설문 결과에 따른 맞춤 상품 추천 API (신용카드/체크카드 공용)
    @PostMapping("/card")
    public List<CreditCardDAO> getRecommendations(
            @RequestParam String cardType,
            @RequestParam(required = false) Integer feeOption,
            @RequestParam String benefits,
            @RequestParam String category) {
        List<CreditCardDAO> list = recommendationService.getRecommendations(cardType, feeOption, benefits, category);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("여기는 컨트롤러 "+list);
        return list;
    }

    // 설문 결과에 따른 맞춤 예적금 상품 추천 API
    @PostMapping("/deposit")
    public List<DepositDAO> getDepositRecommendations(
            @RequestParam String depositType,
            @RequestParam Integer minAmount,
            @RequestParam(required = false) Boolean onlineOnly,
            @RequestParam Integer duration,
            @RequestParam Boolean preferInterestRate) {
        return recommendationService.getDepositRecommendations(depositType, minAmount, onlineOnly, duration, preferInterestRate);
    }
}
