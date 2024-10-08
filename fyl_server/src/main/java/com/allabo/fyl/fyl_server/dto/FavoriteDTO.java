package com.allabo.fyl.fyl_server.dto;

public class FavoriteDTO {
    private Long productId;
    private String productType;  // 금융 상품 종류 (예: 신용카드, 예금 등)


    // Getter and Setter
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
