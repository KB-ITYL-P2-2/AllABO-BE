package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dao.UserLoanAnalyzeDAO;
import com.allabo.fyl.fyl_server.dao.UserSavingAnalyzeDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.security.vo.MyUser;
import com.allabo.fyl.fyl_server.service.UserFinancialsRatioService;
import com.allabo.fyl.fyl_server.service.UserFinancialsService;
import com.allabo.fyl.fyl_server.service.UserLoanAnalyzeService;
import com.allabo.fyl.fyl_server.service.UserSavingAnalyzeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
public class UserLoanAnalyzeController {
    @Value("${openai.api-key}")
    private String openAiApiKey;
    private final UserFinancialsRatioService userFinancialsRatioService;
    private final UserLoanAnalyzeService userLoanAnalyzeService;

    private final UserFinancialsService userFinancialsService;
    @PostMapping("/loan")//결과물: loan분석
    public ReturnClass getSavinganalyze(Authentication authentication, HttpServletRequest request) throws JsonProcessingException {
        MyUser user = (MyUser) authentication.getPrincipal();
        UserFinancialsDTO dto = userFinancialsService.FindUserFinancials(user.getUsername());
        UserFinancialsRatioDAO dao = userFinancialsRatioService.findById(user.getUsername());

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        String requestBody = "{\n" +
                "  \"model\": \"gpt-4o-mini\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"사용자 자산 비율이 아래와 같고,\\n\\n{사용자 총 자산}: "+dto.getTotalAssets()+" 원 (계좌 자산 + 연금 준비 금액 + 투자 평가 금액)\\n\\n{연 소득 대비 부채 비율}: "+dao.getIncomeDebtRatio()+"%\\n{자산 대비 부채 비율}: "+dao.getAssetDebtRatio()+"%\\n\\n{사용자 연령}= 20대야.\\n\\n연령 별 통계가 아래와 같다면,\\n{\\n    \\\"50\\\": {\\n        \\\"연 소득 대비 부채 비율\\\": 78.43342036553524,\\n        \\\"1인 당 자산\\\": 5186.0,\\n        \\\"1인 당 부채\\\": 3830.0,\\n        \\\"자산 대비 부채 비율\\\": 135.4046997389034,\\n        \\\"1인 당 연 소득\\\": 3004.0\\n    },\\n    \\\"20\\\": {\\n        \\\"연 소득 대비 부채 비율\\\": 87.6040703052729,\\n        \\\"1인 당 자산\\\": 5960.0,\\n        \\\"1인 당 부채\\\": 3243.0,\\n        \\\"자산 대비 부채 비율\\\": 183.7804502004317,\\n        \\\"1인 당 연 소득\\\": 2841.0\\n    },\\n    \\\"40\\\": {\\n        \\\"연 소득 대비 부채 비율\\\": 67.0002440810349,\\n        \\\"1인 당 자산\\\": 4770.0,\\n        \\\"1인 당 부채\\\": 4097.0,\\n        \\\"자산 대비 부채 비율\\\": 116.42665364901148,\\n        \\\"1인 당 연 소득\\\": 2745.0\\n    },\\n    \\\"60\\\": {\\n        \\\"연 소득 대비 부채 비율\\\": 80.7566862361383,\\n        \\\"1인 당 자산\\\": 4605.0,\\n        \\\"1인 당 부채\\\": 3066.0,\\n        \\\"자산 대비 부채 비율\\\": 150.19569471624266,\\n        \\\"1인 당 연 소득\\\": 2476.0\\n    },\\n    \\\"30\\\": {\\n        \\\"연 소득 대비 부채 비율\\\": 63.99317406143344,\\n        \\\"1인 당 자산\\\": 5919.0,\\n        \\\"1인 당 부채\\\": 4688.0,\\n        \\\"자산 대비 부채 비율\\\": 126.2585324232082,\\n        \\\"1인 당 연 소득\\\": 3000.0\\n    }\\n}\\n\\n통계와 비교해서 사용자의 자산을 분석해줘.\\n응답은 json.\\n양식은\\n{\\n    \\\"비교군 나이\\\" : 20대,\\n    \\\"같은 연령대 평균과 비교\\\": {\\n        \\\"자산 비교\\\": {\\n            \\\"사용자 자산\\\": \\\"\\\",\\n            \\\"같은 연령대 평균 자산(뒤 0000붙여서)\\\": \\\"\\\",\\n            \\\"사용자 자산>같은 연령대 평균 자산?(true or false)\\\": ,\\n            \\\"사용자와 같은연령대 비율 차이(n배)\\\": \\\"\\\",\\n            \\\"연령 별 자산 총평\\\":\\\"사용자의 자산은 {사용자연령대} 평균 자산인 ?만 원에 비해 약 ?배 이상 높습니다./낮습니다.| {사용자연령대} 평균에 비해 자산이 (크게) 많은/적은 편임을 나타냅니다.\\\",\\n            \\\"#요약키워드(2개)\\\" : []\\n        },\\n        \\\"자산 대비 부채 비율 비교\\\": {\\n            \\\"사용자 자산 대비 부채 비율(% 제외)\\\": ,\\n            \\\"같은 연령대 평균 자산 대비 부채 비율(% 제외,소숫점 두자리수까지)\\\": \\n            \\\"사용자 자산 대비 부채>같은 연령대 자산대비 부채?(true or false)\\\": ,\\n            \\\"사용자와 같은연령대 비율 차이(n배)\\\": ,\\n            \\\"연령 별 자산대비 부채 총평\\\":\\\"사용자의 자산 대비 부채 비율은 ?%로, {사용자연령대} 평균인 ?%에 비해 (매우) 낮습니다/높습니다.\\\",\\n            \\\"#요약키워드(2개)\\\" : []\\n        },\\n        \\\"연 소득 대비 부채 비율 비교\\\": {\\n            \\\"사용자 연 소득 대비 부채 비율(%제외)\\\": ,\\n            \\\"같은 연령대 평균 연 소득 대비 부채 비율(% 제외,소숫점 두자리수까지)\\\": ,\\n            \\\"사용자 소득 대비 부채>같은 연령대 소득 대비 부채?(true or false)\\\": ,\\n            \\\"사용자와 같은연령대 비율 차이(n배)\\\": ,\\n            \\\"# 요약키워드(2개)\\\" : [],\\n            \\\"연령 별 소득 대비 부채 총평\\\":\\\"사용자의 연 소득 대비 부채 비율은 ?%로, {사용자연령대} 평균인 {사용자연령대의 소득 대비 부채비율}%보다 (현저히) 높습니다/낮습니다./평균과유사합니다.\\\"\\n        }\\n    }\\n}\\n\\n내용은 #키워드 형태로 구체적으로 응답해줘. 키워드는 아래에서 중에서 해당되는 뉘앙스로 골라서 표현해줘.\\n\\n### 긍정 키워드 10개\\n1. **#자산크게많음**\\n2. **#재정여유큼**\\n3. **#부채부담적음**\\n4. **#소득대비부채적음**\\n5. **#부채관리우수**\\n6. **#재무상태양호**\\n7. **#저축률높음**\\n8. **#투자비율적정**\\n9. **#건전한재무구조**\\n10. **#재정안정성우수**\\n\\n### 부정 키워드 10개\\n1. **#자산부족**\\n2. **#부채과다**\\n3. **#재정여유부족**\\n4. **#소득대비부채많음**\\n5. **#부채비율높음**\\n6. **#부채관리부실**\\n7. **#저축부족**\\n8. **#투자위험높음**\\n9. **#재무구조취약**\\n10. **#재정안정성미흡**\\n\\n같은 연령대 평균과 얼만큼 차이나는지 구체적으로 응답해줘. #키워드 형태로.\\n키워드는 아래에서 중에서 골라서 표현해줘.\\n\\n@긍정적인 #키워드:\\n\\n#저축우수\\n#자산관리성공\\n#목표달성\\n#경제적안정\\n#저축습관좋음\\n#미래대비\\n#재정건전성\\n#자산성장\\n#효율적저축\\n#재정계획\\n\\n@부정적인 #키워드:\\n\\n#소득불균형\\n#소득대비과다저축\\n#소비부족\\n#현금유동성부족\\n#저축과다\\n#투자기회상실\\n#소득불안정\\n#위험회피성향\\n#소비억제\\n#재정분배불균형\"\n" +
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

        //System.out.println(content);
        if (content.isEmpty()) {
            throw new RuntimeException("The 'conversation' field is empty or missing");
        }

        JsonNode contentNode = objectMapper.readTree(content);
        UserLoanAnalyzeController.ReturnClass returnClass = new UserLoanAnalyzeController.ReturnClass();

// contentNode를 바로 DB에 저장할 준비 (JSON 형태 그대로 저장)
        UserLoanAnalyzeDAO resultDao = new UserLoanAnalyzeDAO();
        resultDao.setId(user.getUsername());
        resultDao.setResults(content); // JSON 데이터를 그대로 저장

// 기존 데이터가 있는지 확인하고 업데이트 또는 삽입
        if (userLoanAnalyzeService.findById(user.getUsername()) != null) {
            userLoanAnalyzeService.updateUserLoanAnalyze(resultDao); // 기존 데이터가 있으면 업데이트
        } else {
            userLoanAnalyzeService.saveUserLoanAnalyze(resultDao); // 없으면 새로운 데이터 저장
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


