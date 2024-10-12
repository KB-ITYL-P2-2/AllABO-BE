package com.allabo.fyl.fyl_server.mapper;

import com.allabo.fyl.fyl_server.dao.CreditCardDAO;
import com.allabo.fyl.fyl_server.dao.LoanDAO;
import com.allabo.fyl.fyl_server.dto.Recommendation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecommendationMapper {

    // 신용카드 추천을 위한 메서드
    List<CreditCardDAO> selectCreditCardRecommendations(@Param("annualFeeMin") Integer annualFeeMin,
                                                        @Param("annualFeeMax") Integer annualFeeMax,
                                                        @Param("benefits") String benefits,
                                                        @Param("category") String category);

    // 체크카드 추천을 위한 메서드
    List<Recommendation> selectCheckCardRecommendations(@Param("benefits") String benefits,
                                                        @Param("category") String category);

//    List<LoanDAO> selectLoanRecommendations()
}
