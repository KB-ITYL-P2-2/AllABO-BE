package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
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
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/insurance")
public class InsuranceRebalancingController {

    @Value("${openai.api-key}")
    private String openAiApiKey;
    @PostMapping("/rebalancing")
    public ResponseEntity<?> getPlan(Authentication authentication, HttpServletRequest request) {
        log.info("Received request for financial plan. Authentication: {}", authentication);

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Unauthorized access attempt to /insurance/rebalancing");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        try {
            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openAiApiKey);

            // 사용자의 보험 데이터를 JSON 문자열로 변환하는 코드
            String requestBody = "{\n" +
                    "    \"model\": \"gpt-4o-mini\",\n" +
                    "    \"messages\": [\n" +
                    "        {\n" +
                    "            \"role\": \"user\",\n" +
                    "            \"content\": \"사용자는 20대야.\\n" +
                    "사용자 보험 내역은 다음과 같아.\\n" +
                    "1. (무)KB Yes!365건강보험\\n" +
                    "가입년월: 2019년 1월\\n" +
                    "월납입금액: 110,000원\\n" +
                    "가입개월차: 68개월\\n" +
                    "2. (무)KB손보실손의료비보장보험\\n" +
                    "가입년월: 2019년 1월\\n" +
                    "월납입금액: 30,000원\\n" +
                    "가입개월차: 68개월\\n" +
                    "3. (무)The드림치아안심보험\\n" +
                    "가입년월: 2018년 4월\\n" +
                    "월납입금액: 30,000원\\n" +
                    "가입개월차: 77개월\\n" +
                    "\\n" +
                    "아래는 연령별 보험 계약 통계야.\\n" +
                    "{\\\"2022\\\": " +
                    "   {\\\"상해\\\": " +
                    "       {\\\"일반상해\\\": " +
                    "           {\\\"10세미만\\\": " +
                    "               {\\\"계약건수\\\": 39458539,\\\"위험보험료\\\": 161422774}, " +
                    "           \\\"1020세미만\\\": " +
                    "               {\\\"계약건수\\\": 47546471, \\\"위험보험료\\\": 226317581}, " +
                    "           \\\"2030세미만\\\": " +
                    "               {\\\"계약건수\\\": 75754162, \\\"위험보험료\\\": 401856161}, " +
                    "           \\\"3040세미만\\\": " +
                    "               {\\\"계약건수\\\": 88013597, \\\"위험보험료\\\": 508443256}, " +
                    "           \\\"4050세미만\\\": " +
                    "               {\\\"계약건수\\\": 106947548, \\\"위험보험료\\\": 698796325}, " +
                    "           \\\"5060세미만\\\": " +
                    "               {\\\"계약건수\\\": 111798887, \\\"위험보험료\\\": 822509590}, " +
                    "           \\\"60세이상\\\": " +
                    "               {\\\"계약건수\\\": 99801360, \\\"위험보험료\\\": 712641601}}," +
                    "       \\\"교통상해\\\": " +
                    "           {\\\"10세미만\\\": {\\\"계약건수\\\": 24347833,\\\"위험보험료\\\": 8527578}, " +
                    "           \\\"1020세미만\\\": {\\\"계약건수\\\": 23810517,\\\"위험보험료\\\": 12228742}, " +
                    "           \\\"2030세미만\\\": {\\\"계약건수\\\": 71884291,\\\"위험보험료\\\": 116492407}, " +
                    "           \\\"3040세미만\\\": {\\\"계약건수\\\": 97006386,\\\"위험보험료\\\": 175893086}, " +
                    "           \\\"4050세미만\\\": {\\\"계약건수\\\": 119415438,\\\"위험보험료\\\": 223376068}, " +
                    "           \\\"5060세미만\\\": {\\\"계약건수\\\": 139709948,\\\"위험보험료\\\": 277584312}, " +
                    "           \\\"60세이상\\\": {\\\"계약건수\\\": 131309696,\\\"위험보험료\\\": 260507685}}}," +
                    "   \\\"질병\\\": " +
                    "       {\\\"전체질병\\\": " +
                    "           {\\\"10세미만\\\": {\\\"계약건수\\\": 17720678,\\\"위험보험료\\\": 211691132}, " +
                    "           \\\"1020세미만\\\": {\\\"계약건수\\\": 15284694,\\\"위험보험료\\\": 52480058}, " +
                    "           \\\"2030세미만\\\": {\\\"계약건수\\\": 23335311,\\\"위험보험료\\\": 97755245}, " +
                    "           \\\"3040세미만\\\": {\\\"계약건수\\\": 25191783,\\\"위험보험료\\\": 151565387}, " +
                    "           \\\"4050세미만\\\": {\\\"계약건수\\\": 29274864,\\\"위험보험료\\\": 357819261}, " +
                    "           \\\"5060세미만\\\": {\\\"계약건수\\\": 29054033,\\\"위험보험료\\\": 625737858}, " +
                    "           \\\"60세이상\\\": {\\\"계약건수\\\": 23540663,\\\"위험보험료\\\": 945093986}}}}\\n" +
                    "통계와 연관지어서 사용자의 보험을 리밸런싱을 제안해줘.\\n" +
                    "답변을 json형태로 오게 해주고\\n" +
                    "유지_권장 부분 값은 부분 유지, 조정 고려, 필수 유지 셋 중에 하나만 오게 해줘." +
                    "제안.건강보험_재조정.설명 부분은 #요약식으로 한 3개정도 오게 해줘 \\\"#보장 부족\\\" 이런 식으로" +
                    "양식은 아래와 같아.\\n" +
                    "{\\\"리밸런싱_제안\\\": {\\\"설명\\\": \\\"\\\",\\\"현재_보험_리스트\\\": {\\\"KB_Yes!365건강보험\\\": {\\\"가입월\\\": \\\"\\\",\\\"월납입금액\\\": 0,\\\"유지_권장\\\": \\\"\\\", \\\"권장_방안\\\":},\\\"KB손보실손의료비보장보험\\\": {\\\"가입월\\\": \\\"\\\",\\\"월납입금액\\\": 0,\\\"유지_권장\\\", \\\"권장_방안\\\": [\\\"#키워드\\\", \\\"#키워드\\\", \\\"#키워드\\\"]\\\"\\\"},\\\"The드림치아안심보험\\\": {\\\"가입월\\\": \\\"\\\",\\\"월납입금액\\\": 0,\\\"유지_권장\\\": \\\"\\\", \\\"권장_방안\\\":}},\\\"제안\\\": {\\\"건강보험_재조정\\\": {\\\"설명\\\": [\\\"#키워드\\\", \\\"#키워드\\\", \\\"#키워드\\\"]\\\"\\\"},\\\"질병보험_추가\\\": {\\\"설명\\\": [\\\"#키워드\\\", \\\"#키워드\\\", \\\"#키워드\\\"]\\\"\\\"},\\\"보험료_최적화\\\": {\\\"설명\\\": [\\\"#키워드\\\", \\\"#키워드\\\", \\\"#키워드\\\"]\\\"\\\"}}}}\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"response_format\": {\"type\": \"json_object\"}\n" +
                    "}";


            System.out.println(requestBody);
            // HTTP 요청 엔티티 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // RestTemplate을 사용하여 OpenAI API 호출
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "https://api.openai.com/v1/chat/completions";

            // 응답 처리
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            log.info("--------------------- OpenAI API Response 받음");
// Jackson ObjectMapper를 사용하여 JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            // 'content' 부분만 추출
            String content = jsonNode.path("choices").get(0).path("message").path("content").asText();

            // 리턴할 구조체 생성
            ReturnClass returnClass = new ReturnClass();

            // JSON 문자열을 Map으로 변환하여 ReturnClass에 세팅
            JsonNode conversationNode = objectMapper.readTree(content);
            returnClass.setRebalancingProposal(conversationNode);

            return ResponseEntity.ok(returnClass);
        } catch (Exception e) {
            log.error("-----------------Error", e);
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class ReturnClass {
        private JsonNode rebalancingProposal;

        public JsonNode getRebalancingProposal() {
            return rebalancingProposal;
        }

        public void setRebalancingProposal(JsonNode rebalancingProposal) {
            this.rebalancingProposal = rebalancingProposal;
        }
    }
}