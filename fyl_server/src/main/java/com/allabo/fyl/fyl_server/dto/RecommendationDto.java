package com.allabo.fyl.fyl_server.dto;

public class RecommendationDto {
    private String preferredCategory; // 선호하는 카테고리
    private int age;
    private int incomeLevel;

    // Getter and Setter
    public String getPreferredCategory() {
        return preferredCategory;
    }

    public void setPreferredCategory(String preferredCategory) {
        this.preferredCategory = preferredCategory;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIncomeLevel() {
        return incomeLevel;
    }

    public void setIncomeLevel(int incomeLevel) {
        this.incomeLevel = incomeLevel;
    }
}
