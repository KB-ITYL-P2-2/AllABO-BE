package com.allabo.fyl.fyl_server.entity;

public class Favorite {
    private Long id;             // 즐겨찾기 고유 ID
    private Long userId;         // 사용자 ID
    private Long productId;      // 금융 상품 ID
    private String productType;  // 금융 상품 유형 (예: 신용카드, 대출, 보험 등)

    // 기본 생성자
    public Favorite() {}

    // 생성자
    public Favorite(Long userId, Long productId, String productType) {
        this.userId = userId;
        this.productId = productId;
        this.productType = productType;
    }

    // Getter & Setter
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
