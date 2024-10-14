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
public class UserFinancialAfterYearController {

    @Value("${openai.api-key}")
    private String openAiApiKey;

    private final UserFinancialsPlanService userFinancialsPlanService;

    public UserFinancialAfterYearController(UserFinancialsPlanService userFinancialsPlanService) {
        this.userFinancialsPlanService = userFinancialsPlanService;
    }

    @PostMapping("/after-year")
    public ResponseEntity<String> compareStrategies(Authentication authentication) {
        log.info("재무 1년후 그래프 요청 받음. 인증 정보: {}", authentication);

        MyUser user = (MyUser) authentication.getPrincipal();
        UserFinancialsDTO dto = userFinancialsPlanService.FindUserFinancials(user.getUsername());
        log.info("사용자 1년후 그래프 조회 완료: {}", user.getUsername());

        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 헤더 설정 (UTF-8 인코딩 적용)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        headers.set("Authorization", "Bearer " + openAiApiKey);

        // JSON 요청 본문 데이터 구성
        String content = "사용자의 현재 재무 상황:\n" +
                "1. 월 소득: " + dto.getMonthlyIncome() + "원\n" +
                "2. 월 지출: " + dto.getTotalExpenses() + "원\n" +
                "3. 총 저축 금액: " + dto.getTotalSavings() + "원\n" +
                "   - 저축 상품: KB내맘대로적금\n" +
                "   - 저축 시작일: 2023년 1월 1일\n" +
                "   - 저축 만기일: 2028년 1월 1일\n" +
                "   - 저축 이자율: 2.5%\n" +
                "4. 총 부채 금액: " + dto.getTotalLoan() + "원\n" +
                "   - 대출 종류: 주택 담보 대출\n" +
                "   - 대출 이자율: 3.5%\n" +
                "   - 대출 만기일: 2033-01-01\n" +
                "5. 총 투자 평가 금액: " + dto.getTotalInvestment() + "원\n" +
                "6. 총 연금 적립 금액: 1,500,000,000원\n" +
                "7. 총 자산: " + dto.getTotalAssets() + "원\n\n" +

                "개선된 전략 요약:\n" +
                "- 지출 조정: 월 지출을 10% 감소하여 저축 및 투자에 더 많은 자금을 할당\n" +
                "- 저축 전략: \n" +
                "  * 목표 저축률: 30%\n" +
                "  * 권장 저축 상품: KB내맘대로적금\n" +
                "- 투자 전략: \n" +
                "  * 목표 투자 비율: 40%\n" +
                "- 부채 관리:\n" +
                "  * 우선순위: 주택 담보 대출\n" +
                "  * 상환 계획: 매달 꾸준히 원금과 이자를 상환\n" +
                "  * 금리 재조정: 현재로서는 필요 없음\n" +
                "  * 추가 상환 전략: 여유 자금으로 추가 상환 고려\n\n" +

                "요청사항:\n" +
                "현재 전략과 개선된 전략을 적용했을 때의 1년 후 예상 자산 변화를 보여주는 그래프 데이터를 생성해줘. 주요 자산 항목(총 자산, 부채, 순자산, 투자금액)에 대해 3개월 단위로 예측값을 제공해주고 모든 숫자는 소수점 둘째 자리에서 반올림해줘.\n" +
                "결과는 다음 JSON 형식으로 부탁해. 아래 결과만 출력하고 다른 내용은 출력하지 말아줘:\n" +
                "{\n" +
                "  \"그래프_데이터\": {\n" +
                "    \"현재_전략\": {\n" +
                "      \"labels\": [\"현재\", \"3개월 후\", \"6개월 후\", \"9개월 후\", \"1년 후\"],\n" +
                "      \"datasets\": [\n" +
                "        { \"label\": \"총 자산\", \"data\": [0, 0, 0, 0, 0] },\n" +
                "        { \"label\": \"총 부채\", \"data\": [0, 0, 0, 0, 0] },\n" +
                "        { \"label\": \"순 자산\", \"data\": [0, 0, 0, 0, 0] },\n" +
                "        { \"label\": \"총 투자금액\", \"data\": [0, 0, 0, 0, 0] }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"개선된_전략\": {\n" +
                "      \"labels\": [\"현재\", \"3개월 후\", \"6개월 후\", \"9개월 후\", \"1년 후\"],\n" +
                "      \"datasets\": [\n" +
                "        { \"label\": \"총 자산\", \"data\": [0, 0, 0, 0, 0] },\n" +
                "        { \"label\": \"총 부채\", \"data\": [0, 0, 0, 0, 0] },\n" +
                "        { \"label\": \"순 자산\", \"data\": [0, 0, 0, 0, 0] },\n" +
                "        { \"label\": \"총 투자금액\", \"data\": [0, 0, 0, 0, 0] }\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"전략_비교_분석\": {\n" +
                "    \"총_자산_변화\": {\n" +
                "      \"현재_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\",\n" +
                "      \"개선된_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\",\n" +
                "      \"차이\": \"?만원 더 (증가/감소/차이없음), ?% 더 (상승/하락/차이없음)\"\n" +
                "    },\n" +
                "    \"부채_변화\": {\n" +
                "      \"현재_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\",\n" +
                "      \"개선된_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\",\n" +
                "      \"차이\": \"?만원 더 (증가/감소/차이없음), ?% 더 (상승/하락/차이없음)\"\n" +
                "    },\n" +
                "    \"순_자산_변화\": {\n" +
                "      \"현재_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\",\n" +
                "      \"개선된_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\",\n" +
                "      \"차이\": \"?만원 더 (증가/감소/차이없음), ?% 더 (상승/하락/차이없음)\"\n" +
                "    },\n" +
                "    \"투자_성과\": {\n" +
                "      \"현재_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\",\n" +
                "      \"개선된_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\",\n" +
                "      \"차이\": \"?만원 더 (증가/감소/차이없음), ?% 더 (상승/하락/차이없음)\"\n" +
                "    },\n" +
                "    \"종합_평가(#키워드)\": \"#키워드 #키워드 하며 #키워드 #키워드 하는 것이 좋겠습니다.\"\n" +
                "  }\n" +
                "}";

        // 요청 데이터를 HttpEntity로 감쌈
        Map<String, Object> data = new HashMap<>();
        data.put("model", "gpt-4");
        data.put("messages", List.of(
                Map.of("role", "system", "content", "You are a helpful assistant. Please provide only the JSON response without any additional text."),
                Map.of("role", "user", "content", content)
        ));
        data.put("temperature", 0);
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


//사용자의 현재 재무 상황
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