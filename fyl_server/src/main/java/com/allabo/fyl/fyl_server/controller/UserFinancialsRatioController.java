package com.allabo.fyl.fyl_server.controller;


import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.security.vo.MyUser;
import com.allabo.fyl.fyl_server.service.UserFinancialsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assets")
public class UserFinancialsRatioController {
    @Value("${openai.api-key}")
    private String openAiApiKey;
    private final UserFinancialsService userFinancialsService;
    @PostMapping("/ratio")//결과물: ratio
    public ReturnClass getRatio(Authentication authentication, HttpServletRequest request){
        MyUser user = (MyUser) authentication.getPrincipal();
        UserFinancialsDTO dto = userFinancialsService.FindUserFinancials(user.getUsername());

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        String requestBody = "{\n" +
                "    \"model\": \"gpt-4o-mini\",\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \"사용자 월 소득: "+dto.getMonthlyIncome()+"원\\n사용자 총 자산: "+dto.getTotalAssets()+"원 (계좌 자산 + 연금 준비 금액 + 투자 평가 금액)\\n사용자 총 저축 금액: "+dto.getTotalSavings()+"원 (계좌 자산)\\n사용자 총 투자 금액: "+dto.getTotalInvestment()+"원 (연금 준비 금액 + 투자 평가 금액)\\n사용자 월 지출 금액: "+dto.getMonthExpenses()+"원 (보험 납부 금액 + 카드 이용 금액)\\n사용자 총 부채 금액: "+dto.getTotalLoan()+"원\\n\\n일 때\\n**1) 사용자 \\\\*자산 대비\\\\* 투자 비율(%)**\\n(총\\\\ 투자\\\\ 금액/총\\\\ 자산) \\\\* 100\\n\\n**2) 사용자 \\\\*연 소득 대비\\\\* 투자 비율(%)**\\n(투자 금액/연 소득) \\\\* 100\\n\\n**3) 사용자 \\\\*연 소득 대비\\\\* 저축 비율(%)**\\n(저축 금액/연 소득) \\\\* 100\\n\\n**4) 사용자 \\\\*연 소득 대비\\\\* 부채 비율(%)**\\n(부채 금액/연 소득) \\\\* 100\\n\\n**5) 사용자 자산 \\\\*대비\\\\* 저축 비율(%)**\\n(저축 금액/총 자산) \\\\* 100\\n\\n**6) 사용자 자산 대비 부채 비율(%)**\\n(저축 금액/지출 금액) \\\\* 100\\n\\n**7) 사용자 월 \\\\*소득 대비\\\\* 지출 비율(%)**\\n(지출 금액/월 소득) \\\\* 100\\n\\n구해줘.\\n응답은 다음과 같이 json형태로. 소숫점 둘째 자리에서 반올림해줘.\\n{\\n  \\\"자산 대비 투자 비율(%)\\\": ,\\n  \\\"연 소득 대비 투자 비율(%)\\\": ,\\n  \\\"연 소득 대비 저축 비율(%)\\\": ,\\n  \\\"연 소득 대비 부채 비율(%)\\\": ,\\n  \\\"자산 대비 저축 비율(%)\\\": ,\\n  \\\"자산 대비 부채 비율(%)\\\": ,\\n  \\\"월 소득 대비 지출 비율(%)\\\": \\n}\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"response_format\":{\"type\": \"json_object\"}\n" +
                "}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "https://api.openai.com/v1/chat/completions";

        ReturnClass returnClass = new ReturnClass();
        returnClass.setConversation(restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        ).getBody());
        return returnClass;
    }

    public static class ReturnClass {
        String conversation;
        public String getConversation() {
            return conversation;
        }
        public void setConversation(String conversation) {
            this.conversation = conversation;
            }
        }

}


