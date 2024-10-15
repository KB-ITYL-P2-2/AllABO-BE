package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dto.Recommendation;
import com.allabo.fyl.fyl_server.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    // 설문 결과에 따른 맞춤 상품 추천 API (신용카드/체크카드 공용)
    @PostMapping("/card")
    public List<Recommendation> getRecommendations(
            @RequestParam String cardType,
            @RequestParam(required = false) Integer feeOption,
            @RequestParam String benefits,
            @RequestParam String category) {
        return recommendationService.getRecommendations(cardType, feeOption, benefits, category);
    }
}
