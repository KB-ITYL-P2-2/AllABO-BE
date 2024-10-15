package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dao.UserFinancialsPlanDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.security.vo.MyUser;
import com.allabo.fyl.fyl_server.service.UserFinancialsPlanService;
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

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assets")
public class UserFinancialsPlanController {

    @Value("${openai.api-key}")
    private String openAiApiKey;

    private final UserFinancialsPlanService userFinancialsPlanService;

    private final ConcurrentHashMap<String, String> financialPlanStorage = new ConcurrentHashMap<>();

    @PostMapping("/plan")
    public ReturnClass createFinancialPlan(Authentication authentication) throws Exception {
        log.info("재무 계획 요청 받음. 인증 정보: {}", authentication);

        MyUser user = (MyUser) authentication.getPrincipal();
        UserFinancialsDTO dto = userFinancialsPlanService.findUserFinancials(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User financials not found for user: " + user.getUsername()));
        log.info("사용자 재무 데이터 조회 완료: {}", user.getUsername());

        String content = "사용자의 자산 현황: \n" +
                "1. 사용자 월 소득: " + dto.getMonthlyIncome() + "원, \n" +
                "2. 사용자 월 지출: " + dto.getMonthExpenses() + "원, \n" +
                "3. 사용자 저축 금액: " + dto.getTotalSavings() + "원 (저축 상품: KB내맘대로적금, 저축 시작일: 2023년 1월 1일, 저축 만기일: 2028년 1월 1일, 저축 이자율: 2.5%), \n" +
                "4. 사용자 부채 금액: " + dto.getTotalLoan() + "원 (대출 종류: 주택 담보 대출, 대출 이자율: 3.5%, 대출 만기일: 2033년 1월 1일), \n" +
                "5. 사용자 총 투자 평가 금액: " + dto.getTotalInvestment() + "원, \n" +
                "6. 사용자 총 연금 적립 금액: 1,500,000,000원, \n" +
                "7. 사용자 총 자산: " + dto.getTotalAssets() + "원. \n\n" +

                "당신은 전문 재무 상담사입니다. 주어진 데이터를 기반으로 사용자의 재무 상황을 분석하고, JSON 형식으로 다음과 같은 전략을 제공해 주세요: \n" +
                "{\n" +
                "    \"사용자_성향_분석\": {\n" +
                "      \"종합_투자_성향\": [\"키워드1\", \"키워드2\", \"키워드3\"],\n" +
                "      \"권장_자산_배분\": {\n" +
                "        \"현금성_자산\": 0,\n" +
                "        \"안전자산\": 0,\n" +
                "        \"위험자산\": 0\n" +
                "      }\n" +
                "    },\n" +
                "   \"개선된_전략_요약\": {\n" +
                "    \"지출_조정\": {\n" +
                "      \"월_지출\": 0,\n" +
                "      \"증가여부\": true,\n" +
                "      \"방안\": \"\"\n" +
                "    },\n" +
                "    \"저축_전략\": {\n" +
                "      \"목표_저축률\": 0,\n" +
                "      \"권장_저축_상품(최대3개)\": [\"상품1\", \"상품2\", \"상품3\"]\n" +
                "    },\n" +
                "    \"투자_전략\": {\n" +
                "      \"목표_투자_비율\": 0,\n" +
                "      \"권장_투자_상품\": [\"상품1\", \"상품2\", \"상품3\"]\n" +
                "    },\n" +
                "    \"부채_관리\": {\n" +
                "      \"우선순위\": \"\",\n" +
                "      \"상환_계획\": \"\",\n" +
                "      \"금리_재조정\": \"\",\n" +
                "      \"추가_상환_전략\": \"\"\n" +
                "    },\n" +
                "    \"결론_및_권고사항\": [\n" +
                "      \"권고사항1(#키워드)\",\n" +
                "      \"권고사항2(#키워드)\",\n" +
                "      \"권고사항3(#키워드)\"\n" +
                "    ]\n" +
                "  }\n" +
                "}\n\n" +

                "분석 시 다음 사항을 고려해주세요:\n" +
                "1. 현재 저축률 및 적정 저축률\n" +
                "2. 부채 대 자산 비율\n" +
                "3. 투자 포트폴리오 다각화 필요성\n" +
                "4. 연금 준비 상태\n" +
                "5. 긴급 자금 확보 여부\n\n" +

                "각 필드에 대한 설명:\n" +
                "- 지출_조정: 현재 지출 대비 조정해야 할 비율과 그 이유를 설명합니다.\n" +
                "- 저축_전략.목표_저축률: 월 소득 대비 권장 저축 비율을 0에서 100 사이의 정수로 제시합니다.\n" +
                "- 저축_전략.권장_저축_상품: 최대 3개의 저축 상품을 추천합니다.\n" +
                "- 투자_전략.목표_투자_비율: 총 자산 대비 권장 투자 비율을 0에서 100 사이의 정수로 제시합니다.\n" +
                "- 투자_전략.권장_투자_상품: 사용자의 상황에 적합한 투자 상품을 추천합니다.\n" +
                "- 부채_관리: 부채 상환의 우선순위, 구체적인 상환 계획, 금리 조정 가능성, 추가 상환 전략 등을 제시합니다.\n" +
                "모든 필드를 빠짐없이 채워주시고, 구체적이고 실행 가능한 조언을 제공해주세요.";

        String response = callOpenAIAPI(content);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(response);
        } catch (Exception e) {
            log.error("JSON 파싱 오류", e);
            throw new RuntimeException("Failed to parse API response", e);
        }

        JsonNode choicesNode = rootNode.path("choices");
        if (!choicesNode.isArray() || choicesNode.size() == 0) {
            throw new RuntimeException("Choices array is empty or missing in the response");
        }

        JsonNode firstChoice = choicesNode.get(0);
        if (firstChoice == null) {
            throw new RuntimeException("First choice is null in the response");
        }

        JsonNode messageNode = firstChoice.path("message");
        if (messageNode.isMissingNode()) {
            throw new RuntimeException("Message node is missing in the response");
        }

        String contentStr = messageNode.path("content").asText();
        if (contentStr.isEmpty()) {
            throw new RuntimeException("The content field is empty or missing");
        }

        JsonNode parsedContent;
        try {
            parsedContent = objectMapper.readTree(contentStr);
        } catch (Exception e) {
            log.error("content JSON 파싱 오류", e);
            throw new RuntimeException("Failed to parse content JSON", e);
        }

        UserFinancialsPlanDAO dao = new UserFinancialsPlanDAO();
        dao.setId(user.getUsername());
        JsonNode improvedStrategy = parsedContent.path("개선된_전략_요약");

        // 지출 조정
        dao.setSpendingAdjustment(improvedStrategy.path("지출_조정").asText());

        // 저축 전략
        dao.setTargetSavingsRate(improvedStrategy.path("저축_전략").path("목표_저축률").asInt());
        dao.setRecommendedSavingsProducts(improvedStrategy.path("저축_전략").path("권장_저축_상품").toString()); // 여기에서 '저축 상품' 배열을 문자열로 저장

        // 투자 전략
        dao.setTargetInvestmentRatio(improvedStrategy.path("투자_전략").path("목표_투자_비율").asInt());
        dao.setRecommendedInvestmentProducts(improvedStrategy.path("투자_전략").path("권장_투자_상품").toString()); // 투자 상품도 마찬가지로 배열을 문자열로 저장

        // 부채 관리
        dao.setDebtPriority(improvedStrategy.path("부채_관리").path("우선순위").asText());
        dao.setDebtRepaymentPlan(improvedStrategy.path("부채_관리").path("상환_계획").asText());
        dao.setInterestRateAdjustment(improvedStrategy.path("부채_관리").path("금리_재조정").asText());
        dao.setAdditionalRepaymentStrategy(improvedStrategy.path("부채_관리").path("추가_상환_전략").asText());


        ReturnClass returnClass = new ReturnClass();
        returnClass.setDao(dao);

        return returnClass;
    }

    private String callOpenAIAPI(String content) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        headers.set("Authorization", "Bearer " + openAiApiKey);

        Map<String, Object> data = new HashMap<>();
        data.put("model", "gpt-3.5-turbo");
        data.put("messages", List.of(
                Map.of("role", "system", "content", "You are a helpful assistant."),
                Map.of("role", "user", "content", content)
        ));
        data.put("max_tokens", 1000);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(data, headers);

        try {
            ResponseEntity<String> response = new RestTemplate().exchange(
                    "https://api.openai.com/v1/chat/completions",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("OpenAI API Response: {}", response.getBody());

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("API request failed with status code: {}", response.getStatusCode());
                throw new RuntimeException("API request failed with status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("OpenAI API 요청 중 오류 발생", e);
            throw new RuntimeException("Error occurred while calling OpenAI API: " + e.getMessage(), e);
        }
    }

    public static class ReturnClass {
        private UserFinancialsPlanDAO dao;

        public UserFinancialsPlanDAO getDao() {
            return dao;
        }

        public void setDao(UserFinancialsPlanDAO dao) {
            this.dao = dao;
        }
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