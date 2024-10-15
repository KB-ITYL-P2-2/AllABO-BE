package com.allabo.fyl.fyl_server.dto;

public class RecommendationDeposits {
    private Long id;
    private String productId;
    private String depositName;
    private String depositContent;
    private String depositCategory;
    private String depositUrl;
    private String depositInterestRate;
    private Integer depositMinAmount;
    private String depositOnline;
    private Integer depositDuration;
    private String depositPreferInterestRate;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDepositName() {
        return depositName;
    }

    public void setDepositName(String depositName) {
        this.depositName = depositName;
    }

    public String getDepositContent() {
        return depositContent;
    }

    public void setDepositContent(String depositContent) {
        this.depositContent = depositContent;
    }

    public String getDepositCategory() {
        return depositCategory;
    }

    public void setDepositCategory(String depositCategory) {
        this.depositCategory = depositCategory;
    }

    public String getDepositUrl() {
        return depositUrl;
    }

    public void setDepositUrl(String depositUrl) {
        this.depositUrl = depositUrl;
    }

    public String getDepositInterestRate() {
        return depositInterestRate;
    }

    public void setDepositInterestRate(String depositInterestRate) {
        this.depositInterestRate = depositInterestRate;
    }

    public Integer getDepositMinAmount() {
        return depositMinAmount;
    }

    public void setDepositMinAmount(Integer depositMinAmount) {
        this.depositMinAmount = depositMinAmount;
    }

    public String getDepositOnline() {
        return depositOnline;
    }

    public void setDepositOnline(String depositOnline) {
        this.depositOnline = depositOnline;
    }

    public Integer getDepositDuration() {
        return depositDuration;
    }

    public void setDepositDuration(Integer depositDuration) {
        this.depositDuration = depositDuration;
    }

    public String getDepositPreferInterestRate() {
        return depositPreferInterestRate;
    }

    public void setDepositPreferInterestRate(String depositPreferInterestRate) {
        this.depositPreferInterestRate = depositPreferInterestRate;
    }
}
