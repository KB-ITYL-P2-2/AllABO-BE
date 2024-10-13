package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dao.*;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.security.vo.MyUser;
import com.allabo.fyl.fyl_server.service.*;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assets")
public class UserSavingRatioAnalyzeController {
    @Value("${openai.api-key}")
    private String openAiApiKey;
    private final UserFinancialsRatioService userFinancialsRatioService;
    private final UserFinancialsService userFinancialsService;
    private final UserIncomeAnalyzeService userIncomeAnalyzeService;
    private final UserSavingRatioAnalyzeService userSavingRatioAnalyzeService;

    @PostMapping("/saving-ratio")//결과물: loan분석
    public ReturnClass getSavingRatioAnalyze(Authentication authentication, HttpServletRequest request) throws JsonProcessingException {
        MyUser user = (MyUser) authentication.getPrincipal();

        UserIncomeAnalyzeDAO idao = userIncomeAnalyzeService.findById(user.getUsername());
        UserFinancialsRatioDAO dao = userFinancialsRatioService.findById(user.getUsername());
        UserFinancialsDTO dto = userFinancialsService.FindUserFinancials(user.getUsername());

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        String requestBody = "{\n" +
                "  \"model\": \"gpt-4o-mini\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"사용자 총 저축 금액: "+dto.getTotalSavings()+"원\\n사용자 자산 비율이 아래와 같고,\\n-자산 대비 저축 비율: "+dao.getAssetSavingsRatio()+"%\\n-연 소득 대비 저축 비율: "+dao.getIncomeSavingsRatio()+"%\\n{사용자연령} = 20대\\n{사용자의 소득분위} = 1분위야.\\n\\n연령 별 통계가 아래와 같다면,\\n{\\n    \\\"20\\\": {\\n        \\\"1인당 평균 저축액\\\": 8750000,\\n        \\\"자산 대비 적립식 저축(적금) 비율\\\": 59.9,\\n        \\\"자산 대비 거치식 저축(정기 예금) 비율\\\": 40.1\\n    },\\n    \\\"30\\\": {\\n        \\\"1인당 평균 저축액\\\": 14350000,\\n        \\\"자산 대비 적립식 저축(적금) 비율\\\": 61.6,\\n        \\\"자산 대비 거치식 저축(정기 예금) 비율\\\": 38.4\\n    },\\n    \\\"40\\\": {\\n        \\\"1인당 평균 저축액\\\": 19880000,\\n        \\\"자산 대비 적립식 저축(적금) 비율\\\": 73.5,\\n        \\\"자산 대비 거치식 저축(정기 예금) 비율\\\": 26.5\\n    },\\n    \\\"50\\\": {\\n        \\\"1인당 평균 저축액\\\": 27470000,\\n        \\\"자산 대비 적립식 저축(적금) 비율\\\": 78.8,\\n        \\\"자산 대비 거치식 저축(정기 예금) 비율\\\": 21.2\\n    },\\n    \\\"60\\\": {\\n        \\\"1인당 평균 저축액\\\": 36480000,\\n        \\\"자산 대비 적립식 저축(적금) 비율\\\": 81.6,\\n        \\\"자산 대비 거치식 저축(정기 예금) 비율\\\": 18.4\\n    }\\n}\\n\\n통계와 비교해서 사용자의 자산을 분석해줘.\\n응답은 json.\\n\\n{사용자연령}을 토대로 {사용자연령평균}을 구해 비교해줘.\\n그리고 반드시 아래 양식대로 출력해줘.\\n{\\n\\t\\\"비교균\\\" : \\\"{사용자 연령대}\\\",\\n\\t\\\"총 저축 금액 분석\\\": \\n\\t{\\n\\t\\t\\\"내 자산\\\" : \\\"{}원\\\",\\n\\t\\t\\\"비교군 자산\\\" : \\\"{}원\\\",\\n\\t\\t\\\"비교군과 비교%\\\" : \\\"{}%\\\",\\n\\t\\t\\\"내 자산>비교군 자산\\\" : \\\"true or false\\\",\\n\\t\\t\\\"요약\\\" : [\\\"#키워드\\\", \\\"#키워드\\\"] \\n\\t},\\n\\t\\\"자산 대비 저축 비율 분석\\\":{\\n\\t\\t\\\"자산 대비 저축 비율\\\" : \\\"{}%\\\",\\n\\t\\t\\\"비교균 저축 비율\\\" : \\\"{}%\\\",\\n\\t\\t\\\"차이\\\" : \\\"{}%\\\",\\n\\t\\t\\\"요약\\\" : [\\\"#키워드\\\", \\\"#키워드\\\"]\\n\\t} \\n}\\n\\n같은 연령대 평균과 얼만큼 차이나는지 구체적으로 응답해줘. #키워드 형태로.\\n키워드는 아래에서 중에서 골라서 표현해줘.\\n\\n@긍정적인 #키워드:\\n\\n#저축우수\\n#자산관리성공\\n#목표달성\\n#경제적안정\\n#저축습관좋음\\n#미래대비\\n#재정건전성\\n#자산성장\\n#효율적저축\\n#재정계획\\n\\n\\n@부정적인 #키워드:\\n\\n#소득불균형\\n#소득대비과다저축\\n#소비부족\\n#현금유동성부족\\n#저축과다\\n#투자기회상실\\n#소득불안정\\n#위험회피성향\\n#소비억제\\n#재정분배불균형\"\n" +
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

        String content = rootNode.path("choices").get(0).path("message").path("content").asText();

        //System.out.println(content);
        if (content.isEmpty()) {
            throw new RuntimeException("The 'conversation' field is empty or missing");
        }

        JsonNode contentNode = objectMapper.readTree(content);
        UserSavingRatioAnalyzeController.ReturnClass returnClass = new UserSavingRatioAnalyzeController.ReturnClass();

// contentNode를 바로 DB에 저장할 준비 (JSON 형태 그대로 저장)
        UserSavingRatioAnalyzeDAO resultDao = new UserSavingRatioAnalyzeDAO();
        resultDao.setId(user.getUsername());
        resultDao.setResults(content); // JSON 데이터를 그대로 저장

// 기존 데이터가 있는지 확인하고 업데이트 또는 삽입
        if (userSavingRatioAnalyzeService.findById(user.getUsername()) != null) {
            userSavingRatioAnalyzeService.updateUserSavingRatioAnalyze(resultDao); // 기존 데이터가 있으면 업데이트
        } else {
            userSavingRatioAnalyzeService.saveUserRatioSavingAnalyze(resultDao); // 없으면 새로운 데이터 저장
        }

// contentNode를 그대로 리턴
        returnClass.setJsonNode(contentNode); // 사용자 단에 리턴할 값 설정
        return returnClass;
    }


    public static class ReturnClass {
        private JsonNode jsonNode;

        public JsonNode getJsonNode() {
            return jsonNode;
        }

        public void setJsonNode(JsonNode jsonNode) {
            this.jsonNode = jsonNode;
        }
    }


}


