package com.allabo.fyl.fyl_server.dto;

import lombok.Data;

import java.util.List;

@Data
public class ImprovedStrategyDTO {
    private String 지출_조정;
    private 저축전략 저축_전략;
    private 투자전략 투자_전략;
    private 부채관리 부채_관리;

    @Data
    public static class 저축전략 {
        private double 목표_저축률;
        private List<String> 권장_저축_상품;
    }

    @Data
    public static class 투자전략 {
        private double 목표_투자_비율;
        private List<String> 권장_투자_상품;
    }

    @Data
    public static class 부채관리 {
        private String 우선순위;
        private String 상환_계획;
        private String 금리_재조정;
        private String 추가_상환_전략;
    }
}