package com.allabo.fyl.fyl_server.security.controller;

import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.security.mapper.CustomerMapper;
import com.allabo.fyl.fyl_server.security.util.JWTUtil;
import com.allabo.fyl.fyl_server.security.vo.Customer;
import com.allabo.fyl.fyl_server.security.vo.MyUser;
import com.allabo.fyl.fyl_server.service.UserFinancialsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
//@RequiredArgsConstructor
@Slf4j
public class AssetAnalyzeController {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private UserFinancialsService service;

    @Autowired
    private JWTUtil jwtUtil;
    @GetMapping("/assets/analyze")
    public ResponseEntity<String> Assetanalyze(Authentication authentication, HttpServletRequest request) {
        String id = authentication.getName();
        MyUser myUser = (MyUser)authentication.getPrincipal();
//        log.info("자산분석 들어온 손님: " + myUser.getCustomer().getIdentityNumber()
//        );
        String identityNumber = myUser.getCustomer().getIdentityNumber();

        if (identityNumber == null || identityNumber.isEmpty()) {
            throw new RuntimeException("Identity number is missing");
        }
        log.info("identityNumber: " + identityNumber);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/kb/total";

        try {
// JWT 토큰 생성
            Map<String, Object> claims = new HashMap<>();
            claims.put("identityNumber", identityNumber); // 클레임에 identityNumber 추가
            String jwtToken = jwtUtil.generateToken(claims, 1); // 1일 동안 유효한 JWT 토큰 생성

// JWT 토큰을 Authorization 헤더에 추가
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

// GET 요청 시 JWT 토큰을 헤더에 추가하여 보냄
//
            ResponseEntity<Map> apiResponse = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            log.info("....{}", apiResponse);
            if (apiResponse.getStatusCode().is2xxSuccessful()) {

                Map<String, Object> totalData = apiResponse.getBody();
                log.info("KB로부터 Total data from API: {}", totalData);
                UserFinancialsDTO dto = new UserFinancialsDTO();
                dto.setId(myUser.getCustomer().getId());
                dto.setMonthlyIncome(myUser.getCustomer().getTotalIncome() / 12);
                dto.setTotalSavings((Integer) totalData.getOrDefault("totalAccountBalance", 0)); // 총 저축
                dto.setTotalAssets(10000000 + (Integer) totalData.getOrDefault("totalAccountBalance", 0)); // 종자산(저축 + 투자)
                dto.setTotalInvestment(10000000); // 총 투자
                dto.setTotalExpenses(
                        (Integer) totalData.getOrDefault("totalCardAmount", 0) +
                                (Integer) totalData.getOrDefault("totalInsurancePremium", 0)
                ); // 총 지출
                dto.setTotalLoan((Integer) totalData.getOrDefault("totalRemainingBalance", 0)); // 대출금
                log.info("financial dto: {}", dto);
                service.processAndSaveUserFinancial(dto);
                return ResponseEntity.ok().build();


        } else{

//            log.error("API 호출 실패. 상태 코드: {}", apiResponse.getStatusCode());
            throw new RuntimeException("Failed to retrieve data from API");
        }
        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



}
