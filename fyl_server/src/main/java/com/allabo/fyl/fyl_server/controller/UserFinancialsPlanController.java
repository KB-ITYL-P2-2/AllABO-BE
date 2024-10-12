package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.service.UserFinancePlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assets")
public class UserFinancialsPlanController {

    @Value("${openai.api-key}")
    private String openAiApiKey;
    private final UserFinancePlanService userFinancePlanService;

    @PostMapping("/plan")
    public ResponseEntity<?> getPlan(Authentication authentication, HttpServletRequest request) {
        log.info("Received request for financial plan. Authentication: {}", authentication);

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Unauthorized access attempt to /assets/plan");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        try {
            // 사용자 정보 가져오기
            UserFinancialsDTO dto = userFinancePlanService.FindUserFinancials(authentication.getName());
            log.info("Retrieved financial data for user: {}", authentication.getName());

            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openAiApiKey);

            // 월 지출을 연간 지출로 변환
            double monthlyExpenditure = dto.getTotalExpenses() / 12.0;

            // ChatGPT API 요청 바디 생성
            String requestBody = "{\n" +
                    "    \"model\": \"gpt-4o-mini\",\n" +
                    "    \"messages\": [\n" +
                    "        {\n" +
                    "            \"role\": \"user\",\n" +
                    "            \"content\": \"사용자의 자산 현황은 다음과 같습니다:\\n" +
                    "1. 사용자 월 소득: " + dto.getMonthlyIncome() + "만원\\n" +
                    "2. 사용자 월 지출: " + monthlyExpenditure + "만원\\n" +
                    "3. 사용자 저축 금액: " + dto.getTotalSavings() + "원\\n" +
                    "    1. 저축 상품: " + dto.getSavingsProductName() + "\\n" +
                    "    2. 저축 시작일: " + dto.getSavingsStartDate() + "\\n" +
                    "    3. 저축 만기일: " + dto.getSavingsEndDate() + "\\n" +
                    "    4. 저축 이자율: " + dto.getSavingsInterestRate() + "%\\n" +
                    "4. 사용자 부채 금액: " + dto.getTotalLoan() + "만원\\n" +
                    "    1. 대출 종류: " + dto.getLoanType() + "\\n" +
                    "    2. 대출 이자율: " + dto.getLoanInterestRate() + "%\\n" +
                    "    3. 대출 만기일: " + dto.getLoanEndDate() + "\\n" +
                    "5. 사용자 보험 월 납입금: " + dto.getInsuranceMonthlyPayment() + "원\\n" +
                    "    1. 보험 상품: " + dto.getInsuranceProduct() + "\\n" +
                    "    2. 보험 가입 일시: " + dto.getInsuranceStartDate() + "\\n" +
                    "6. 사용자 총 투자 평가 금액: " + dto.getTotalInvestment() + "만원\\n" +
                    "7. 사용자 총 연금 적립 금액: " + dto.getTotalPension() + "만원\\n" +
                    "8. 사용자 총 자산: " + dto.getTotalAssets() + "만원\\n\\n" +
                    "사용자의 투자, 저축 비율 분석 결과는 다음과 같습니다:\\n" +
                    "{ " +
                    "  '자산 대비 투자 비율 분석': '보수적인 투자 성향', " +
                    "  '연 소득 대비 투자 비율 분석': '중립적 투자 성향', " +
                    "  '자산 대비 저축 비율 분석': '균형 잡힌 성향' " +
                    "}\\n\\n" +
                    "주어진 데이터를 기반으로 사용자의 재무 상황을 분석하고, 개선된 자산 관리 전략을 제안해 주세요.\\n결과는 JSON 형식으로 제공해 주세요.\\n\" " +
                    "        }\n" +
                    "    ],\n" +
                    "    \"response_format\":{\"type\": \"json_object\"}\n" +
                    "}";

            // HTTP 요청 엔티티 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // RestTemplate을 사용하여 OpenAI API 호출
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "https://api.openai.com/v1/chat/completions";

            // 응답 처리
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("--------------------- OpenAI API Response 받음");

            ReturnClass returnClass = new ReturnClass();
            returnClass.setConversation(response.getBody());

            return ResponseEntity.ok(returnClass);
        } catch (Exception e) {
            log.error("-----------------Error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");
        }
    }

    public static class ReturnClass {
        String conversation;
        public String getConversation() {
            return conversation;
        }
        public void setConversation(String conversation) {
            this.conversation = conversation;
        }
    }
}