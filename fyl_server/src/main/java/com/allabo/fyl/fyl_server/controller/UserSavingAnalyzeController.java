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
                "      \"content\": \"사용자의 자산 비율이 아래와 같고,\\n\\n{\\n  \\\"자산 대비 투자 비율(%)\\\": "+dao.getAssetInvestmentRatio()+",\\n  \\\"연 소득 대비 투자 비율(%)\\\": "+dao.getIncomeInvestmentRatio()+",\\n  \\\"연 소득 대비 저축 비율(%)\\\": "+dao.getIncomeSavingsRatio()+",\\n  \\\"연 소득 대비 부채 비율(%)\\\": "+dao.getIncomeDebtRatio()+",\\n  \\\"자산 대비 저축 비율(%)\\\": "+dao.getAssetSavingsRatio()+",\\n  \\\"자산 대비 부채 비율(%)\\\": "+dao.getAssetDebtRatio()+",\\n  \\\"연 소득 대비 지출 비율(%)\\\": "+dao.getIncomeExpenditureRatio()+"\\n}\\n\\n투자유형이 아래와 같이 분류된다면,\\na.자산 대비 투자 비율:\\n20% 이하: 보수적인 투자 성향\\n20% ~ 50%: 중립적 투자 성향\\n50% 이상: 공격적인 투자 성향\\n\\nb.연 소득 대비 투자 비율:\\n10% 이하: 보수적인 투자 성향\\n10% ~ 50%: 중립적 투자 성향\\n50% 이상: 공격적인 투자 성향\\n\\nc.자산 대비 저축 비율:\\n50% 이하: 투자 성향이 강함\\n50% ~ 70%: 균형 잡힌 성향\\n70% 이상: 저축 성향이 강함\\n\\n\\n사용자의 투자 유형을 각각 분석해줘.\\n응답은 json. 양식은 아래와 같이 \\n{\\n    \\\"resultMap\\\": {\\n        \\\"사용자가 강한 성향\\\": \\\"저축 성향 or 투자 성향 or 소비성향\\\",\\n        \\\"공격적인 성향(true or false)\\\": ,\\n        \\\"자산 대비\\\" : [\\\"~한 저축 성향\\\",\\\"~한 투자 성향\\\",\\\"~한 소비성향\\\"]\\n    }\\n}\\n\\n꼭 누락되는 거 없이 작성해줘.\"\n" +
                "    }\n" +
                "  ]," +
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
        String content = rootNode.path("choices").get(0).path("message").path("content").asText();
        System.out.println(content);

        if (content.isEmpty()) {
            throw new RuntimeException("The 'content' field is empty or missing");
        }

// content를 다시 JSON으로 파싱
        JsonNode contentNode = objectMapper.readTree(content);

// resultMap 필드만 추출
        JsonNode resultMapNode = contentNode.path("resultMap");

// resultMap 필드가 문자열로 되어 있는 경우, 다시 JSON으로 파싱
        if (resultMapNode.isTextual()) {
            // resultMap 필드가 문자열로 들어있는 경우 다시 파싱
            resultMapNode = objectMapper.readTree(resultMapNode.asText());
        }

// resultMapNode를 보기 좋은 문자열로 변환
        String resultMapString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMapNode);

// ReturnClass에 resultMap 설정하여 리턴 (문자열 대신 JsonNode로 설정 가능)
        UserSavingAnalyzeController.ReturnClass returnClass = new UserSavingAnalyzeController.ReturnClass();
        returnClass.setResultMap(resultMapNode);  // 사용자에게 리턴 (JsonNode 직접 설정)


// DB 저장 로직
        UserSavingAnalyzeDAO resultDao = new UserSavingAnalyzeDAO();
        resultDao.setId(user.getUsername());
        resultDao.setResults(resultMapString);

// 사용자 분석 정보가 이미 존재하면 업데이트, 그렇지 않으면 저장
        if (userSavingAnalyzeService.findById(user.getUsername()) != null) {
            userSavingAnalyzeService.updateUserSavingAnalyze(resultDao);
        } else {
            userSavingAnalyzeService.saveUserSavingAnalyze(resultDao);
        }

        return returnClass;  // 최종 리턴

    }


    public static class ReturnClass {
        private JsonNode resultMap;

        public JsonNode getResultMap() {
            return resultMap;
        }
        public void setResultMap(JsonNode resultMap) {
            this.resultMap = resultMap;
        }
    }

}


