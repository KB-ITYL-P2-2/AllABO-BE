package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dto.KBTotalDTO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.security.vo.Customer;
import com.allabo.fyl.fyl_server.repository.UserFinancialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
//public class UserFinancialsServiceImpl{
//
//}
public class UserFinancialsServiceImpl implements UserFinancialsService{
    private final UserFinancialsRepository userFinancialsRepository;

    public void processAndSaveUserFinancial(Customer customer) {

        UserFinancialsDTO dto = new UserFinancialsDTO();
//        dto.setUserEmail(customer.getEmail());
//
//        // 사용자 월 소득 계산 (연 소득 / 12)
//        dto.setMonthlyIncome(String.valueOf(customer.getTotalIncome() / 12));
//
//        // 사용자 총 저축 금액 계산 (계좌 자산 + 정기 예금 + 적금)
//        String totalSavings = calculateTotalSavings(customer); // 직접 계산
//
//        // 사용자 총 투자 금액 계산
//        String totalInvestment = calculateTotalInvestment(customer); // 직접 계산
//
//        // 사용자 총 자산 계산 (저축 + 투자)
//        dto.setTotalAssets(String.valueOf(Integer.parseInt(totalSavings) + Integer.parseInt(totalInvestment)));
//
//        // 사용자 총 지출 금액 계산 (보험납부금액 + 카드 이용금액)
//        dto.setTotalExpenses(String.valueOf(kbTotal.getTotalInsurancePremium() + kbTotal.getTotalCardAmount()));
//
//        // 사용자 총 부채 금액 설정
//        dto.setTotalLoan(String.valueOf(kbTotal.getTotalRemainingBalance()));
//
//        // 레포지토리를 통해 데이터베이스에 저장
//        userFinancialsRepository.saveUserFinancial(dto);
//    }
//
//    // 저축 금액 계산 로직
//    private String calculateTotalSavings(Customer customer) {
        // 예시로 임의의 금액 계산
        //return String.valueOf(customer.getAccountBalance() + customer.getRegularDeposit() + customer.getSavings());
    }

    // 투자 금액 계산 로직
//    private String calculateTotalInvestment(Customer customer) {
//        // 예시로 임의의 투자 금액 계산
//        return String.valueOf(customer.getInvestmentEvaluation());
//    }

    @Override
    public void processAndSaveUserFinancial(Customer customer, KBTotalDTO kbTotal) {

    }
}
