package com.allabo.fyl.fyl_server.entity;

public class Favorite {
    private int favoritesId;             // 즐겨찾기 고유 ID
    private String id;         // 사용자 ID
    private String productId;      // 금융 상품 ID
    private int productNum;  // 금융 상품 유형 (예: 신용카드, 대출, 보험 등)

    public int getFavoritesId() {
        return favoritesId;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setFavoritesId(int favoritesId) {
        this.favoritesId = favoritesId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }
}
