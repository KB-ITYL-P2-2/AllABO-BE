package com.allabo.fyl.fyl_server.dto;

public class Recommendation {
    private Long id;
    private String cardName;
    private Integer annualFee;
    private String benefits;
    private String category;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Integer getAnnualFee() {
        return annualFee;
    }

    public void setAnnualFee(Integer annualFee) {
        this.annualFee = annualFee;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
