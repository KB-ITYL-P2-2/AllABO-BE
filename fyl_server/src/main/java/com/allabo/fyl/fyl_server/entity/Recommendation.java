package com.allabo.fyl.fyl_server.entity;

public class Recommendation {
    private Long id;
    private String name;             // 상품 이름 (카드 이름)
    private String benefits;         // 혜택 (필터링 기준)
    private Double annualFee;        // 연회비
    private Double usageLimit;       // 사용 한도
    private String issuer;           // 발급사

    // 기본 생성자
    public Recommendation() {}

    // 생성자
    public Recommendation(Long id, String name, String benefits, Double annualFee, Double usageLimit, String issuer) {
        this.id = id;
        this.name = name;
        this.benefits = benefits;
        this.annualFee = annualFee;
        this.usageLimit = usageLimit;
        this.issuer = issuer;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public Double getAnnualFee() {
        return annualFee;
    }

    public void setAnnualFee(Double annualFee) {
        this.annualFee = annualFee;
    }

    public Double getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Double usageLimit) {
        this.usageLimit = usageLimit;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
