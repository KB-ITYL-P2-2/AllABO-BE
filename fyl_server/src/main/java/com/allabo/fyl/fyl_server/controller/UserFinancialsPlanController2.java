package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.security.vo.MyUser;
import com.allabo.fyl.fyl_server.service.UserFinancePlanService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/assets")
public class UserFinancialsPlanController2 {

    @Value("${openai.api-key}")
    private String openAiApiKey;

    private final UserFinancePlanService userFinancePlanService;

    public UserFinancialsPlanController2(UserFinancePlanService userFinancePlanService) {
        this.userFinancePlanService = userFinancePlanService;
    }

    @PostMapping("/plan2")
    public ResponseEntity<String> createRequestBody(Authentication authentication) {
        log.info("재무 계획 요청 받음. 인증 정보: {}", authentication);

        MyUser user = (MyUser) authentication.getPrincipal();
        UserFinancialsDTO dto = userFinancePlanService.FindUserFinancials(user.getUsername());
        log.info("사용자 재무 데이터 조회 완료: {}", user.getUsername());

        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 헤더 설정 (UTF-8 인코딩 적용)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        headers.set("Authorization", "Bearer " + openAiApiKey);

        // JSON 요청 본문 데이터 구성
        String content = "사용자의 자산 현황은 다음과 같습니다: " +
                "1. 사용자 월 소득: " + dto.getMonthlyIncome() + "원, " +
                "2. 사용자 월 지출: " + dto.getMonthExpenses() + "원, " +
                "3. 사용자 저축 금액: " + dto.getTotalSavings() + "원, " +
                "4. 사용자 부채 금액: " + dto.getTotalLoan() + "원, " +
                "5. 사용자 총 투자 평가 금액: " + dto.getTotalInvestment() + "원, " +
                "6. 사용자 총 연금 적립 금액: 1500000000원, " +
                "7. 사용자 총 자산: " + dto.getTotalAssets() + "원. " +
                "주어진 데이터를 기반으로 사용자의 재무 상황을 분석하고, JSON 형식으로 다음과 같은 전략을 제공해 주세요: {" +
                "\"그래프_데이터\": {" +
                "  \"현재_전략\": {" +
                "    \"labels\": [\"현재\", \"3개월 후\", \"6개월 후\", \"9개월 후\", \"1년 후\"]," +
                "    \"datasets\": [" +
                "      { \"label\": \"총 자산\", \"data\": [0, 0, 0, 0, 0] }," +
                "      { \"label\": \"총 부채\", \"data\": [0, 0, 0, 0, 0] }," +
                "      { \"label\": \"순 자산\", \"data\": [0, 0, 0, 0, 0] }," +
                "      { \"label\": \"총 투자금액\", \"data\": [0, 0, 0, 0, 0] }" +
                "    ]" +
                "  }," +
                "  \"개선된_전략\": {" +
                "    \"labels\": [\"현재\", \"3개월 후\", \"6개월 후\", \"9개월 후\", \"1년 후\"]," +
                "    \"datasets\": [" +
                "      { \"label\": \"총 자산\", \"data\": [0, 0, 0, 0, 0] }," +
                "      { \"label\": \"총 부채\", \"data\": [0, 0, 0, 0, 0] }," +
                "      { \"label\": \"순 자산\", \"data\": [0, 0, 0, 0, 0] }," +
                "      { \"label\": \"총 투자금액\", \"data\": [0, 0, 0, 0, 0] }" +
                "    ]" +
                "  }" +
                "}," +
                "\"전략_비교_분석\": {" +
                "  \"총_자산_변화\": {" +
                "    \"현재_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\"," +
                "    \"개선된_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\"," +
                "    \"차이\": \"?만원 더 (증가/감소/차이없음), ?% 더 (상승/하락/차이없음)\"" +
                "  }," +
                "  \"부채_변화\": {" +
                "    \"현재_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\"," +
                "    \"개선된_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\"," +
                "    \"차이\": \"?만원 더 (증가/감소/차이없음), ?% 더 (상승/하락/차이없음)\"" +
                "  }," +
                "  \"순_자산_변화\": {" +
                "    \"현재_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\"," +
                "    \"개선된_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\"," +
                "    \"차이\": \"?만원 더 (증가/감소/차이없음), ?% 더 (상승/하락/차이없음)\"" +
                "  }," +
                "  \"투자_성과\": {" +
                "    \"현재_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\"," +
                "    \"개선된_전략\": \"?만원 (증가/감소/차이없음), ?% (상승/하락/차이없음)\"," +
                "    \"차이\": \"?만원 더 (증가/감소/차이없음), ?% 더 (상승/하락/차이없음)\"" +
                "  }," +
                "  \"종합_평가(#키워드)\": \"#키워드 #키워드 하며 #키워드 #키워드 하는 것이 좋겠습니다.\"" +
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
