package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dao.UserExpenditureAnalyzeDAO;
import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dao.UserIncomeAnalyzeDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.security.vo.MyUser;
import com.allabo.fyl.fyl_server.service.UserExpenditureAnalyzeService;
import com.allabo.fyl.fyl_server.service.UserFinancialsRatioService;
import com.allabo.fyl.fyl_server.service.UserFinancialsService;
import com.allabo.fyl.fyl_server.service.UserIncomeAnalyzeService;
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
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assets")
public class UserExpensesRatioController {
    @Value("${openai.api-key}")
    private String openAiApiKey;
    private final UserFinancialsRatioService userFinancialsRatioService;
    private final UserFinancialsService userFinancialsService;
    private final UserExpenditureAnalyzeService userExpenditureAnalyzeService;
    private final UserIncomeAnalyzeService userIncomeAnalyzeService;
    @PostMapping("/expenditure")
    public ReturnClass getExpensesRatioAnalyze(Authentication authentication, HttpServletRequest request) throws JsonProcessingException {
        MyUser user = (MyUser) authentication.getPrincipal();
        UserFinancialsRatioDAO dao = userFinancialsRatioService.findById(user.getUsername());
        UserFinancialsDTO dto = userFinancialsService.FindUserFinancials(user.getUsername());
        UserIncomeAnalyzeDAO incomeDAO = userIncomeAnalyzeService.findById(user.getUsername());
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        String requestBody = "{\n" +
                "  \"model\": \"gpt-4o-mini\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"사용자의 월 소득은 "+dto.getMonthlyIncome()+" 원,\\n사용자의 월 소득 대비 지출 비율: "+dao.getIncomeExpenditureRatio()+"%\\n사용자의 연령은 20대야.\\n사용자는 "+incomeDAO.getIncomeLev()+"에 속해.\\n\\n\\n아래는 1인당 월 지출 통계야.\\n{\\n    \\\"1분위\\\": {\\n        \\\"2024-Q2\\\": {\\n            \\\"1인당 지출\\\": 1406949.6604697215,\\n            \\\"소득대비 소비지출(%)\\\": 106.84837497124835,\\n            \\\"1인당 소득\\\": 1316772.1650875038\\n        }\\n    },\\n    \\\"2분위\\\": {\\n        \\\"2024-Q2\\\": {\\n            \\\"1인당 지출\\\": 2054041.8031484107,\\n            \\\"소득대비 소비지출(%)\\\": 84.17521548382626,\\n            \\\"1인당 소득\\\": 2440197.8555588988\\n        }\\n    },\\n    \\\"3분위\\\": {\\n        \\\"2024-Q2\\\": {\\n            \\\"1인당 지출\\\": 2561106.1066334243,\\n            \\\"소득대비 소비지출(%)\\\": 80.96610926541375,\\n            \\\"1인당 소득\\\": 3163182.879688466\\n        }\\n    },\\n    \\\"4분위\\\": {\\n        \\\"2024-Q2\\\": {\\n            \\\"1인당 지출\\\": 3168622.6579583664,\\n            \\\"소득대비 소비지출(%)\\\": 77.48356207995303,\\n            \\\"1인당 소득\\\": 4089412.738522213\\n        }\\n    },\\n    \\\"5분위\\\": {\\n        \\\"2024-Q2\\\": {\\n            \\\"1인당 지출\\\": 4400294.542155083,\\n            \\\"소득대비 소비지출(%)\\\": 66.785913025058,\\n            \\\"1인당 소득\\\": 6588656.713436106\\n        }\\n    }\\n}\\n\\n\\n통계 기반으로 사용자의 자산을 분석해줘. 사용자가 속하는 분위를 골라서, 비교해줘\\n응답은 json.\\n양식은\\n{\\n  \\\"사용자가 속하는 분위(n분위)\\\": \\\"\\\",\\n  \\\"같은 분위의 1인당 소득 대비 지출 비율차(%제외)\\\": \\\"\\\",\\n  \\\"같은 분위 소득대비 소비지출 비율보다 초과or미만\\\": \\\"\\\",\\n  \\\"#요약키워드\\\": []\\n}\\n\\n#요약키워드는 아래 중에서 골라서 표현해줘.\\n<긍정 키워드>\\n#자산크게많음, #재정여유큼, #부채부담적음, #소득대비부채적음, #부채관리우수, #재무상태양호, #저축률높음, #투자비율적정, #건전한재무구조, #재정안정성우수\\n\\n<부정 키워드>\\n#재정여유부족, #소득대비부채많음, #지출과다, #소비많음, #저축부족, #부채관리부실, #재무구조취약, #재정안정성미흡, #자산부족, #부채비율높음\\n\\n꼭 누락되는 거 없이 작성해줘.\"\n" +
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
        Map<String, Object> resultMap = new HashMap<>();
        Iterator<String> fieldNames = contentNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldValueNode = contentNode.get(fieldName);
            if (fieldValueNode.isTextual()) {
                resultMap.put(fieldName, fieldValueNode.asText());
            }
            // 배열일 경우
            else if (fieldValueNode.isArray()) {
                // 배열 요소를 List<String>으로 변환하여 저장
                List<String> arrayValues = new ArrayList<>();
                for (int i = 0; i < fieldValueNode.size(); i++) {
                    arrayValues.add(fieldValueNode.get(i).asText());
                }
                resultMap.put(fieldName, arrayValues); // 배열 형태로 저장
            }
            // 다른 값일 경우 (숫자, 객체 등)
            else {
                resultMap.put(fieldName, fieldValueNode.toString());
            }
        }

// ReturnClass에 resultMap 설정하여 리턴 (문자열 대신 JsonNode로 설정 가능)
        UserExpensesRatioController.ReturnClass returnClass = new UserExpensesRatioController.ReturnClass();
        returnClass.setResultMap(resultMap);//사용자단에 리턴
        String jsonString = objectMapper.writeValueAsString(resultMap);//for saving to DB
        UserExpenditureAnalyzeDAO resultDao = new UserExpenditureAnalyzeDAO();
        resultDao.setId(user.getUsername());
        resultDao.setResults(jsonString);

// 사용자 분석 정보가 이미 존재하면 업데이트, 그렇지 않으면 저장
        if (userExpenditureAnalyzeService.findById(user.getUsername()) != null) {
            userExpenditureAnalyzeService.updateUserExpenditureAnalyze(resultDao);
        } else {
            userExpenditureAnalyzeService.saveUserExpenditureAnalyze(resultDao);
        }

        return returnClass;  // 최종 리턴

    }


    public static class ReturnClass {
        private Map<String, Object> resultMap;

        public Map<String, Object> getResultMap() {
            return resultMap;
        }
        public void setResultMap(Map<String, Object> resultMap) {
            this.resultMap = resultMap;
        }
    }

}


