package com.allabo.fyl.fyl_server.mapper;

import com.allabo.fyl.fyl_server.entity.Recommendation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecommendationMapper {

<<<<<<< Updated upstream
    // 설문 결과에 따른 맞춤 상품 필터링
    List<Recommendation> selectRecommendationsBySurvey(
            @Param("benefits") String benefits,
            @Param("annualFeeLimit") Double annualFeeLimit,
            @Param("issuer") String issuer
    );
=======
    List<Recommendation> selectCreditCardRecommendations(@Param("annualFee") Integer annualFee,
                                                         @Param("benefits") String benefits,
                                                         @Param("category") String category);
>>>>>>> Stashed changes
}
