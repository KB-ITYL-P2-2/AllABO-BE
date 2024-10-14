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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assets")
public class UserFinancialAfterYearController {

    @Value("${openai.api-key}")
    private String openAiApiKey;
    private final UserFinancePlanService userFinancePlanService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/after-year")
    public ResponseEntity<String> compareStrategies(Authentication authentication, HttpServletRequest request) {
        log.info("재무 계획 요청 받음.");

        // 사용자 정보 조회
        MyUser user = (MyUser) authentication.getPrincipal();
        UserFinancialsDTO dto = userFinancePlanService.FindUserFinancials(user.getUsername());
        log.info("사용자 재무 데이터 조회 완료");

        // 요청 본문 생성
        String requestBody = createRequestBody(dto);
        log.info("OpenAI API 요청 본문: {}", requestBody.substring(0, Math.min(requestBody.length(), 500)));

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        // OpenAI API 호출
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
        } catch (Exception e) {
            log.error("OpenAI API 요청 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("OpenAI API 요청 중 오류가 발생했습니다.");
        }

        log.info("OpenAI API 응답 수신 완료");

        // 응답 처리
        try {
            String content = extractContentFromResponse(response.getBody());
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            log.error("응답 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("응답 처리 중 오류가 발생했습니다.");
        }
    }

    private String createRequestBody(UserFinancialsDTO dto) {
        String content = "사용자의 자산 현황은 다음과 같습니다: " +
                "1. 사용자 월 소득: " + dto.getMonthlyIncome() + "원, " +
                "2. 사용자 월 지출: " + dto.getMonthExpenses() + "원, " +
                "3. 사용자 저축 금액: " + dto.getTotalSavings() + "원 (저축 상품: KB내맘대로적금, 저축 시작일: 2023년 1월 1일, 저축 만기일: 2028년 1월 1일, 저축 이자율: 2.5%), " +
                "4. 사용자 부채 금액: " + dto.getTotalLoan() + "원 (대출 종류: 주택 담보 대출, 대출 이자율: 3.5%, 대출 만기일: 2033-01-01), " +
                "5. 사용자 총 투자 평가 금액: " + dto.getTotalInvestment() + "원, " +
                "6. 사용자 총 연금 적립 금액: 1500000000원, " +
                "7. 사용자 총 자산: " + dto.getTotalAssets() + "원. " +
                "주어진 데이터를 기반으로 사용자의 재무 상황을 분석하고, JSON 형식으로 다음과 같은 전략을 제공해 주세요. 다른말 생략하고 JSON만 딱 리턴해주세요. 이스케이프문 없이 키:값 형태로만 뽑아줘. : {" +
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
                "  \"종합_평가(#키워드)\": \"#키워드 #키워드 하며 #키워드 #키워드 하는 것이 좋겠습니다.\"" +
                "}" +
                "}";

        return String.format("{\"model\": \"gpt-4\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"temperature\": 0, \"max_tokens\": 1000, \"stop\": null}", content.replace("\"", "\\\"").replace("\n", "\\n"));
    }

    // OpenAI API 응답에서 content 부분을 파싱하여 반환하는 메서드 수정
    private String extractContentFromResponse(String responseBody) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode choicesNode = rootNode.path("choices");

        if (choicesNode.isArray() && choicesNode.size() > 0) {
            JsonNode firstChoice = choicesNode.get(0);
            JsonNode messageNode = firstChoice.path("message");
            JsonNode contentNode = messageNode.path("content");

            if (contentNode.isTextual()) {
                // 이스케이프 문자를 처리하지 않고 JSON으로 직접 반환
                return contentNode.asText();
            }
        }
        throw new Exception("응답에서 content를 찾을 수 없습니다.");
    }


    public static class ReturnClass {
        String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}