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

        String requestBody = "{\"model\": \"gpt-4-vision-preview\", \"messages\": [{\"role\": \"user\", \"content\": [{\"type\": \"text\", \"text\": \"사진에 보이는 쓰레기들을 어떻게 분리수거 할 수 있는지 번호를 매겨서 설명해줘\"}, {\"type\": \"image_url\", \"image_url\": {\"url\": \"" + "" + "\"}}]}], \"max_tokens\": 1000}";

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
        String url, conversation;
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public String getConversation() {
            return conversation;
        }
        public void setConversation(String conversation) {
            this.conversation = conversation;
            }
        }

}


