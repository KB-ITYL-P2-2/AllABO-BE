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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assets")
public class UserFinancialsPlanController {

    @Value("${openai.api-key}")
    private String openAiApiKey;
    private final UserFinancePlanService userFinancePlanService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/plan")
    public ReturnClass getPlan(Authentication authentication, HttpServletRequest request) {
        log.info("재무 계획 요청 받음. 인증 정보: {}", authentication);

        MyUser user = (MyUser) authentication.getPrincipal();

        try {
            UserFinancialsDTO dto = userFinancePlanService.FindUserFinancials(user.getUsername());
            log.info("사용자 재무 데이터 조회 완료: {}", user.getUsername());

            String requestBody = createRequestBody(dto);
            log.info("OpenAI API 요청 본문: {}", requestBody.substring(0, Math.min(requestBody.length(), 1000)));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openAiApiKey);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "https://api.openai.com/v1/chat/completions";

            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("OpenAI API 응답 수신 완료");

            String content = extractContentFromResponse(response.getBody());
            ReturnClass returnClass = new ReturnClass();
            returnClass.setContent(content);
            return returnClass;

        } catch (HttpClientErrorException e) {
            log.error("OpenAI API 클라이언트 오류: {}", e.getResponseBodyAsString(), e);
            ReturnClass errorReturn = new ReturnClass();
            errorReturn.setContent("OpenAI API 오류: " + e.getResponseBodyAsString());
            return errorReturn;
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            ReturnClass errorReturn = new ReturnClass();
            errorReturn.setContent("예상치 못한 오류가 발생했습니다. 나중에 다시 시도해 주세요.");
            return errorReturn;
        }
    }

    private String createRequestBody(UserFinancialsDTO dto) {
        String content = "사용자의 자산 현황은 다음과 같습니다:\\n" +
                "1. 사용자 월 소득: " + dto.getMonthlyIncome() + "원\\n" +
                "2. 사용자 월 지출: " + (dto.getMonthExpenses()) + "원\\n" +
                "3. 사용자 저축 금액: " + dto.getTotalSavings() + "원\\n" +
                "   1. 저축 상품: " + dto.getSavingsProductName() + "\\n" +
                "   2. 저축 시작일: " + dto.getSavingsStartDate() + "\\n" +
                "   3. 저축 만기일: " + dto.getSavingsEndDate() + "\\n" +
                "   4. 저축 이자율: " + dto.getSavingsInterestRate() + "%\\n" +
                "4. 사용자 부채 금액: " + dto.getTotalLoan() + "원\\n" +
                "   1. 대출 종류: " + dto.getLoanType() + "\\n" +
                "   2. 대출 이자율: " + dto.getLoanInterestRate() + "%\\n" +
                "   3. 대출 만기일: " + dto.getLoanEndDate() + "\\n" +
                "5. 사용자 보험 월 납입금: " + dto.getInsuranceMonthlyPayment() + "원\\n" +
                "   1. 보험 상품: " + dto.getInsuranceProduct() + "\\n" +
                "   2. 보험 가입 일시: " + dto.getInsuranceStartDate() + "\\n" +
                "6. 사용자 총 투자 평가 금액: " + dto.getTotalInvestment() + "원\\n" +
                "7. 사용자 총 연금 적립 금액: " + dto.getTotalPension() + "원\\n" +
                "8. 사용자 총 자산: " + dto.getTotalAssets() + "원\\n\\n" +
                "사용자의 투자, 저축 비율 분석 결과는 다음과 같습니다:\\n" +
                "{\\n" +
                "   \\\"자산 대비 투자 비율 분석\\\": \\\"보수적인 투자 성향\\\",\\n" +
                "   \\\"연 소득 대비 투자 비율 분석\\\": \\\"중립적 투자 성향\\\",\\n" +
                "   \\\"자산 대비 저축 비율 분석\\\": \\\"균형 잡힌 성향\\\"\\n" +
                "}\\n\\n" +
                "주어진 데이터를 기반으로 사용자의 재무 상황을 분석하고, 개선된 자산 관리 전략을 제안해 주세요.\\n" +
                "결과는 JSON 형식으로 제공해 주세요.\\n\\n" +
                "{\\n" +
                "    \\\"사용자_성향_분석\\\": {\\n" +
                "        \\\"종합_투자_성향(#키워드 나열)\\\": \\\"\\\",\\n" +
                "        \\\"권장_자산_배분\\\": {\\n" +
                "            \\\"현금성_자산\\\": 0,\\n" +
                "            \\\"안전자산\\\": 0,\\n" +
                "            \\\"위험자산\\\": 0\\n" +
                "        }\\n" +
                "    },\\n" +
                "    \\\"개선된_전략_요약\\\": {\\n" +
                "        \\\"지출_조정\\\": \\\"월 지출을 ?% (감소/유지/증가)하여 {개선 방안}을 고려해야 합니다.\\\",\\n" +
                "        \\\"저축_전략\\\": {\\n" +
                "            \\\"목표_저축률\\\": 0,\\n" +
                "            \\\"권장_저축_상품\\\": []\\n" +
                "        },\\n" +
                "        \\\"투자_전략\\\": {\\n" +
                "            \\\"목표_투자_비율\\\": 0,\\n" +
                "            \\\"권장_포트폴리오\\\": {\\n" +
                "                \\\"현금성_자산\\\": 0,\\n" +
                "                \\\"안전자산\\\": 0,\\n" +
                "                \\\"위험자산\\\": 0\\n" +
                "            },\\n" +
                "            \\\"권장_투자_상품\\\": []\\n" +
                "        },\\n" +
                "        \\\"부채_관리\\\": {\\n" +
                "            \\\"우선순위\\\": \\\"\\\",\\n" +
                "            \\\"상환_계획\\\": \\\"\\\",\\n" +
                "            \\\"금리_재조정\\\": \\\"\\\",\\n" +
                "            \\\"추가_상환_전략\\\": \\\"\\\"\\n" +
                "        },\\n" +
                "        \\\"보험_최적화\\\": \\\"1.\\\", \\\"2.\\\"\\n" +
                "    },\\n" +
                "    \\\"주요_재무_지표\\\": {\\n" +
                "        \\\"부채비율\\\": 0,\\n" +
                "        \\\"저축률\\\": 0,\\n" +
                "        \\\"투자수익률\\\": 0,\\n" +
                "        \\\"부채상환비율\\\": 0\\n" +
                "    },\\n" +
                "    \\\"결론_및_권고사항(#키워드)\\\": \\\"\\\"\\n" +
                "}";

        return String.format(
                "{\n" +
                        "    \"model\": \"gpt-4\",\n" +
                        "    \"messages\": [\n" +
                        "        {\n" +
                        "            \"role\": \"user\",\n" +
                        "            \"content\": \"%s\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}", content.replace("\"", "\\\"").replace("\n", "\\n"));
    }
    

    private String extractContentFromResponse(String responseBody) throws Exception {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode choicesNode = rootNode.path("choices");
        if (choicesNode.isArray() && choicesNode.size() > 0) {
            JsonNode firstChoice = choicesNode.get(0);
            JsonNode messageNode = firstChoice.path("message");
            JsonNode contentNode = messageNode.path("content");
            if (contentNode.isTextual()) {
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