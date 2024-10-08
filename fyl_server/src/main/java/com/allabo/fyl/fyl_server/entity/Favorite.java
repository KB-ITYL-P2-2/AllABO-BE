package com.allabo.fyl.fyl_server.entity;

public class Favorite {
    private Long id;
    private Long userId;
    private Long productId;
    private String productType;  // 금융 상품 종류 (예: 신용카드, 예금 등)

    // 기본 생성자
    public Favorite() {}

    // 생성자
    public Favorite(Long userId, Long productId, String productType) {
        this.userId = userId;
        this.productId = productId;
        this.productType = productType;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
