package com.allabo.fyl.fyl_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoriteInsertDTO {
    private Long userId;
    private Long productId;
    private String productType;  // 금융 상품 종류 (예: 신용카드, 예금 등)

}
