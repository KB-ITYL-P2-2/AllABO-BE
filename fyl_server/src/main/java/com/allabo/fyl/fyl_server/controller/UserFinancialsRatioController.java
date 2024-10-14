package com.allabo.fyl.fyl_server.controller;


import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.security.vo.MyUser;
import com.allabo.fyl.fyl_server.service.UserFinancialsRatioService;
import com.allabo.fyl.fyl_server.service.UserFinancialsService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assets")
public class UserFinancialsRatioController {
    @Value("${openai.api-key}")
    private String openAiApiKey;
    private final UserFinancialsService userFinancialsService;
    private final UserFinancialsRatioService userFinancialsRatioService;

    @PostMapping("/ratio")//결과물: ratio
    public ReturnClass getRatio(Authentication authentication) throws JsonProcessingException {
        MyUser user = (MyUser) authentication.getPrincipal();
        UserFinancialsDTO dto = userFinancialsService.FindUserFinancials(user.getUsername());

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        String requestBody = "{\n" +
                "  \"model\": \"gpt-4o-mini\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"사용자 월 소득: "+dto.getMonthlyIncome() +"원\\n사용자 총 자산: "+dto.getTotalAssets()+" 원 \\n사용자 총 저축 금액: "+dto.getTotalSavings() +"원 (계좌 자산)\\n사용자 총 투자 금액: "+dto.getTotalInvestment()+"원 (연금 준비 금액 + 투자 평가 금액)\\n사용자 월 지출 금액: "+dto.getMonthExpenses()+" 원 (보험 납부 금액 + 카드 이용 금액)\\n사용자 총 부채 금액: "+dto.getTotalLoan()+"원\\n\\n일 때\\n**1) 사용자 *자산 대비* 투자 비율(%)**\\n(사용자 총 투자 금액/사용자 총 자산) * 100\\n\\n**2) 사용자 *연 소득 대비* 투자 비율(%)**\\n(사용자 총 투자 금액/사용자 월 소득x12) * 100\\n\\n**3) 사용자 *연 소득 대비* 저축 비율(%)**\\n(사용자 총 저축 금액/사용자 월 소득x12) * 100\\n\\n**4) 사용자 *연 소득 대비* 부채 비율(%)**\\n(사용자 총 부채 금액/사용자 월 소득x12) * 100\\n\\n**5) 사용자 자산 *대비* 저축 비율(%)**\\n(사용자 총 저축 금액/사용자 총 자산) * 100\\n\\n**6) 사용자 자산 대비 부채 비율(%)**\\n(사용자 총 부채 금액/사용자 총 자산) * 100\\n\\n**7) 사용자 월 *소득 대비* 지출 비율(%)**\\n(사용자 월 소득/사용자 월 지출 금액) * 100\\n\\n구해줘.\\n응답은 다음과 같이 json형태로. 소숫점 둘째 자리에서 반올림해줘.\\n{\\n  \\\"자산 대비 투자 비율(%)\\\": ,\\n  \\\"연 소득 대비 투자 비율(%)\\\": ,\\n  \\\"연 소득 대비 저축 비율(%)\\\": ,\\n  \\\"연 소득 대비 부채 비율(%)\\\": ,\\n  \\\"자산 대비 저축 비율(%)\\\": ,\\n  \\\"자산 대비 부채 비율(%)\\\": ,\\n  \\\"월 소득 대비 지출 비율(%)\\\": \\n}\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"response_format\": {\n" +
                "    \"type\": \"json_object\"\n" +
                "  }\n" +
                "}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "https://api.openai.com/v1/chat/completions";
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody());
//
//        // 로그로 확인 (실제 응답 데이터)
//        System.out.println("Response Body: " + response.getBody());

        String content = rootNode.path("choices").get(0).path("message").path("content").asText();  // JSON에서 content 필드를 가져옴


        if (content.isEmpty()) {
            throw new RuntimeException("The 'conversation' field is empty or missing");
        }

        JsonNode contentNode = objectMapper.readTree(content);
        Map<String, Integer> resultMap = new HashMap<>();

        Iterator<String> fieldNames = contentNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            int fieldValue = contentNode.get(fieldName).asInt();
            resultMap.put(fieldName, fieldValue);  // Map에 키와 값을 저장
        }

        ReturnClass returnClass = new ReturnClass();
        returnClass.setResultMap(resultMap);
        UserFinancialsRatioDAO dao = new UserFinancialsRatioDAO();
        dao.setId(user.getUsername());
        dao.setAssetSavingsRatio(resultMap.get("자산 대비 저축 비율(%)"));
        dao.setAssetInvestmentRatio(resultMap.get("자산 대비 투자 비율(%)"));
        dao.setIncomeExpenditureRatio(resultMap.get("월 소득 대비 지출 비율(%)"));
        dao.setIncomeSavingsRatio(resultMap.get("연 소득 대비 저축 비율(%)"));
        dao.setAssetDebtRatio(resultMap.get("자산 대비 부채 비율(%)"));
        dao.setIncomeInvestmentRatio(resultMap.get("연 소득 대비 투자 비율(%)"));
        dao.setIncomeDebtRatio(resultMap.get("연 소득 대비 부채 비율(%)"));
        if(userFinancialsRatioService.findById(user.getUsername())!=null){
            userFinancialsRatioService.updateUserFinancialRatio(dao);
        }else{
            userFinancialsRatioService.saveUserFinancialsRatio(dao);
        }
        return returnClass;
    }

    public static class ReturnClass {
        private Map<String, Integer> resultMap;

//        public String getConversation() {
//            return conversation;
//        }
//
//        public void setConversation(String conversation) {
//            this.conversation = conversation;
//        }


    public Map<String, Integer> getResultMap() {
        return resultMap;
    }



    public void setResultMap(Map<String, Integer> resultMap) {
        this.resultMap = resultMap;
    }

}

}


