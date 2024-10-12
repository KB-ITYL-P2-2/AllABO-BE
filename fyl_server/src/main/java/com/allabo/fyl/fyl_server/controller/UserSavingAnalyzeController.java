package com.allabo.fyl.fyl_server.controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dao.UserSavingAnalyzeDAO;
import com.allabo.fyl.fyl_server.security.vo.MyUser;
import com.allabo.fyl.fyl_server.service.UserFinancialsRatioService;
import com.allabo.fyl.fyl_server.service.UserSavingAnalyzeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
public class UserSavingAnalyzeController {
    @Value("${openai.api-key}")
    private String openAiApiKey;
    private final UserFinancialsRatioService userFinancialsRatioService;
    private final UserSavingAnalyzeService userSavingAnalyzeService;
    @PostMapping("/saving")//결과물: saving분석
    public ReturnClass getSavinganalyze(Authentication authentication, HttpServletRequest request) throws JsonProcessingException {
        MyUser user = (MyUser) authentication.getPrincipal();
        UserFinancialsRatioDAO dao = userFinancialsRatioService.findById(user.getUsername());

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        String requestBody = "{\n" +
                "  \"model\": \"gpt-4o-mini\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"사용자의 자산 비율이 아래와 같고,\\n{\\n  \\\"자산 대비 투자 비율(%)\\\": "+dao.getAssetInvestmentRatio()+",\\n  \\\"연 소득 대비 투자 비율(%)\\\": "+dao.getIncomeInvestmentRatio()+",\\n  \\\"연 소득 대비 저축 비율(%)\\\": "+dao.getIncomeSavingsRatio()+",\\n  \\\"연 소득 대비 부채 비율(%)\\\": "+dao.getIncomeDebtRatio()+",\\n  \\\"자산 대비 저축 비율(%)\\\": "+dao.getAssetSavingsRatio()+",\\n  \\\"자산 대비 부채 비율(%)\\\": "+dao.getAssetDebtRatio()+",\\n  \\\"월 소득 대비 지출 비율(%)\\\": "+dao.getIncomeExpenditureRatio()+"\\n}\\n\\n투자유형이 아래와 같이 분류된다면,\\na.자산 대비 투자 비율:\\n20% 이하: 보수적인 투자 성향\\n20% ~ 50%: 중립적 투자 성향\\n50% 이상: 공격적인 투자 성향\\n\\n\\nb.연 소득 대비 투자 비율:\\n10% 이하: 보수적인 투자 성향\\n10% ~ 50%: 중립적 투자 성향\\n50% 이상: 공격적인 투자 성향\\n\\nc.자산 대비 저축 비율:\\n50% 이하: 투자 성향이 강함\\n50% ~ 70%: 균형 잡힌 성향\\n70% 이상: 저축 성향이 강함\\n\\n\\n사용자의 투자 유형을 각각 분석해주고 종합 분석까지 해줘.\\n응답은 json. 양식은 아래와 같이 \\n{\\n  \\\"자산 대비 투자 비율 분석\\\": \\\"\\\",\\n  \\\"연 소득 대비 투자 비율 분석\\\": \\\"\\\",\\n  \\\"자산 대비 저축 비율 분석\\\": \\\"\\\",\\n  \\\"종합 분석\\\": \\\"\\\"\\n}\\n\\n나머지는 명사형으로,\\n종합 분석 내용은 \\\"사용자는 전반적으로 {저축성향or투자성향or소비성향}이 ~하지만or~하고,|{공격적or소극적} ~성향|을 가지고 있습니다.|자산 대비로는 ~ 투자|을(를) 보이며,|저축 비율도 ~ 편|입니다.\\\" 형태로 응답해줘.\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"response_format\": {\n" +
                "    \"type\": \"json_object\"\n" +
                "  }\n" +
                "}\n";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "https://api.openai.com/v1/chat/completions";
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody());

        String content = rootNode.path("choices").get(0).path("message").path("content").asText();


        if (content.isEmpty()) {
            throw new RuntimeException("The 'conversation' field is empty or missing");
        }

        JsonNode contentNode = objectMapper.readTree(content);
        Map<String, String> resultMap = new HashMap<>();


        Iterator<String> fieldNames = contentNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            String fieldValue = contentNode.get(fieldName).asText();
            resultMap.put(fieldName, fieldValue);
        }

        UserSavingAnalyzeController.ReturnClass returnClass = new UserSavingAnalyzeController.ReturnClass();
        returnClass.setResultMap(resultMap);//사용자단에 리턴
        String jsonString = objectMapper.writeValueAsString(resultMap);//for saving to DB
        UserSavingAnalyzeDAO resultDao = new UserSavingAnalyzeDAO();
        resultDao.setId(user.getUsername());
        resultDao.setResults(jsonString);
        if(userSavingAnalyzeService.findById(user.getUsername())!=null){
            userSavingAnalyzeService.updateUserSavingAnalyze(resultDao);
        }else{
            userSavingAnalyzeService.saveUserSavingAnalyze(resultDao);
        }
        return returnClass;
    }


    public static class ReturnClass {
        private Map<String, String> resultMap;

        public Map<String, String> getResultMap() {
            return resultMap;
        }
        public void setResultMap(Map<String, String> resultMap) {
            this.resultMap = resultMap;
        }
    }

}


