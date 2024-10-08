package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.entity.Recommendation;
import com.allabo.fyl.fyl_server.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    // 설문 결과에 따른 맞춤 상품 추천 API
    @GetMapping("/survey")
    public List<Recommendation> getRecommendations(
            @RequestParam(required = false) String benefits,
            @RequestParam(required = false) Double annualFeeLimit,
            @RequestParam(required = false) String issuer
    ) {
        return recommendationService.getRecommendationsBySurvey(benefits, annualFeeLimit, issuer);
    }
}
