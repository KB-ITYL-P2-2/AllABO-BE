package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.security.vo.MyUser;
import com.allabo.fyl.fyl_server.service.UserFinancialsPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/assets")
public class UserFinancialsPlanController {

    @Value("${openai.api-key}")
    private String openAiApiKey;

    private final UserFinancialsPlanService userFinancialsPlanService;

    public UserFinancialsPlanController(UserFinancialsPlanService userFinancialsPlanService) {
        this.userFinancialsPlanService = userFinancialsPlanService;
    }

    @PostMapping("/plan")
    public ResponseEntity<String> createRequestBody(Authentication authentication) {
        log.info("재무 계획 요청 받음. 인증 정보: {}", authentication);

        MyUser user = (MyUser) authentication.getPrincipal();
        UserFinancialsDTO dto = userFinancialsPlanService.FindUserFinancials(user.getUsername());
        log.info("사용자 재무 데이터 조회 완료: {}", user.getUsername());

        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 헤더 설정 (UTF-8 인코딩 적용)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        headers.set("Authorization", "Bearer " + openAiApiKey);

        // JSON 요청 본문 데이터 구성
        String content = "사용자의 자산 현황: " +
                "1. 사용자 월 소득: " + dto.getMonthlyIncome() + "원, " +
                "2. 사용자 월 지출: " + dto.getMonthExpenses() + "원, " +
                "3. 사용자 저축 금액: " + dto.getTotalSavings() + "원 (저축 상품: KB내맘대로적금, 저축 시작일: 2023년 1월 1일, 저축 만기일: 2028년 1월 1일, 저축 이자율: 2.5%), " +
                "4. 사용자 부채 금액: " + dto.getTotalLoan() + "원 (대출 종류: 주택 담보 대출, 대출 이자율: 3.5%, 대출 만기일: 2033-01-01), " +
                "5. 사용자 총 투자 평가 금액: " + dto.getTotalInvestment() + "원, " +
                "6. 사용자 총 연금 적립 금액: 1500000000원, " +
                "7. 사용자 총 자산: " + dto.getTotalAssets() + "원. " +
                "주어진 데이터를 기반으로 사용자의 재무 상황을 분석하고, JSON 형식으로 다음과 같은 전략을 제공해 줘. 다른말 생략하고 JSON만 딱 리턴해주세요. 이스케이프문 없이 키:값 형태로만 뽑아줘. : {" +
                "\"개선된_전략_요약\": {" +
                "   \"지출_조정\": \"월 지출을 ?% (감소/유지/증가)하여 {개선 방안}을 고려해야 합니다.\"," +
                "   \"저축_전략\": {" +
                "       \"목표_저축률\": 0," +
                "       \"권장_저축_상품(최대 3개)\": []" +
                "   }," +
                "   \"투자_전략\": {" +
                "       \"목표_투자_비율\": 0," +
                "       }," +
                "       \"권장_투자_상품\": []" +
                "   }," +
                "   \"부채_관리\": {" +
                "       \"우선순위\": \"\"," +
                "       \"상환_계획\": \"\"," +
                "       \"금리_재조정\": \"\"," +
                "       \"추가_상환_전략\": \"\"" +
                "   }" +
                "}" +
                "}";

        // 요청 데이터를 HttpEntity로 감쌈
        Map<String, Object> data = new HashMap<>();
        data.put("model", "gpt-3.5-turbo");
        data.put("messages", List.of(
                Map.of("role", "system", "content", "You are a helpful assistant."),
                Map.of("role", "user", "content", content)
        ));
        data.put("max_tokens", 1000);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(data, headers);

        try {
            // POST 요청 보내기
            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://api.openai.com/v1/chat/completions",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            // 응답 처리
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null) {
                    Map<String, Object> choices = (Map<String, Object>) ((List<Object>) responseBody.get("choices")).get(0);
                    Map<String, String> message = (Map<String, String>) choices.get("message");
                    String contentResponse = message.get("content");

                    HttpHeaders responseHeaders = new HttpHeaders();
                    responseHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
                    return ResponseEntity.ok()
                            .headers(responseHeaders)
                            .body(contentResponse);
                }
            }
        } catch (Exception e) {
            log.error("OpenAI API 요청 중 오류 발생", e);
            return ResponseEntity.status(500).body("Error occurred while calling OpenAI API: " + e.getMessage());
        }

        return ResponseEntity.status(500).body("No response from OpenAI API.");
    }
}


//"사용자의 자산 현황은 다음과 같습니다:\\n" +
//    "1. 사용자 월 소득: " + dto.getMonthlyIncome() + "원\\n" +
//    "2. 사용자 월 지출: " + dto.getMonthExpenses() + "원\\n" +
//    "3. 사용자 저축 금액: " + dto.getTotalSavings() + "원\\n" +
//    "   1. 저축 상품: " + dto.getSavingsProductName() + "\\n" +
//    "   2. 저축 시작일: " + dto.getSavingsStartDate() + "\\n" +
//    "   3. 저축 만기일: " + dto.getSavingsEndDate() + "\\n" +
//    "   4. 저축 이자율: " + dto.getSavingsInterestRate() + "%\\n" +
//    "4. 사용자 부채 금액: " + dto.getTotalLoan() + "원\\n" +
//    "   1. 대출 종류: " + dto.getLoanType() + "\\n" +
//    "   2. 대출 이자율: " + dto.getLoanInterestRate() + "%\\n" +
//    "   3. 대출 만기일: " + dto.getLoanEndDate() + "\\n" +
//    "5. 사용자 보험 월 납입금: " + dto.getInsuranceMonthlyPayment() + "원\\n" +
//    "   1. 보험 상품: " + dto.getInsuranceProduct() + "\\n" +
//    "   2. 보험 가입 일시: " + dto.getInsuranceStartDate() + "\\n" +
//    "6. 사용자 총 투자 평가 금액: " + dto.getTotalInvestment() + "원\\n" +
//    "7. 사용자 총 연금 적립 금액: " + dto.getTotalPension() + "원\\n" +
//    "8. 사용자 총 자산: " + dto.getTotalAssets() + "원\\n";