package com.allabo.fyl.fyl_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFinancialsDTO {
    private String id;
    private int monthlyIncome;
    private int totalAssets;
    private int totalSavings;
    private int totalInvestment;
    private int totalExpenses;
    private int monthExpenses;
    private int totalLoan;

    // 계좌 내역 테이블 매핑
    private String accountType;  // 계좌 타입 (계좌 내역의 account_type)
    private String savingsProductName;  // 저축 상품 이름 (계좌 내역의 account_number)
    private String savingsStartDate;    // 저축 시작일 (계좌 내역의 savings_start_date)
    private String savingsEndDate;      // 저축 만기일 (계좌 내역의 savings_end_date)
    private double savingsInterestRate; // 저축 이자율 (계좌 내역의 savings_interest_rate)

    // 대출 정보 테이블 매핑
    private String loanType;           // 대출 종류 (대출 정보의 loan_type)
    private double loanInterestRate;   // 대출 이자율 (대출 정보의 interest_rate)
    private String loanStartDate;      // 대출 시작일 (대출 정보의 loan_start_date)
    private String loanEndDate;        // 대출 만기일 (대출 정보의 loan_end_date)
    private int remainingLoan;         // 남은 대출 금액 (대출 정보의 remaining_balance)

    // 보험 내역 테이블 매핑
    private int insuranceMonthlyPayment;  // 보험 월 납입금 (보험 내역의 monthly_premium)
    private String insuranceProduct;      // 보험 상품 이름 (보험 내역의 insurance_name)
    private String insuranceStartDate;    // 보험 가입일 (보험 내역의 subscription_start_date)

    // 연금 관련 정보 (DB에 명시된 정보는 없지만 필요시 추가 가능)
    private int totalPension;  // 연금 적립 금액
}
