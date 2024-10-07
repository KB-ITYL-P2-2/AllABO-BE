package com.allabo.fyl.fyl_server.security.filter;

import com.allabo.fyl.fyl_server.security.exception.RefreshTokenException;
import com.allabo.fyl.fyl_server.security.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 1.전송된 JSON데이터에서 accessToken과 refreshToken을 추출<br>
 * 2.accessToken을 검사해서 토큰이 없거나 잘못된 토큰인 경우 에러 메시지 전송<br>
 *   ex)토큰없는경우 - ("msg": "NO_ACCESS")<br>
 *        토크만료  - ("msg": Expired Token)<br>
 * 3. refreshToken을 검사해서 토큰이 없거나 잘못된 토큰 혹은 만료된 토큰인 경우 에러 메시지 전송<br>
 *   ex)토큰없는경우- ("msg": "NO_REFRESH")<br>
 *             만료- ("msg": "OLD_REFRESH")<br>
 *
 * 4.새로운 accessToken생성<br>
 * 5. 테스트용 refresh token의 만료기한은 10분으로 설정되어있다. 만료 기한이 얼마 남지 않은 경우( ex: 테스트로는 3분) 새로운  refreshToken생성<br>
 * 6.새로운 accessToken과 refreshToken전송<br>
 */

@Slf4j
@RequiredArgsConstructor
public class MyRefreshTokenFilter extends OncePerRequestFilter {

    private final String refreshPath;
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (!path.equals(refreshPath)) {
            log.info("skip refresh token filter.....: refreshPath:{}, path:{}" , refreshPath, path);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Refresh Token Filter...run..............1");

        //전송된 JSON에서 accessToken과 refreshToken을 얻어온다.
        Map<String, String> tokens = getJSonData(request);
        log.info("tokens: " + tokens);
        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        log.info("accessToken: " + accessToken);
        log.info("refreshToken: " + refreshToken);

        try{
            checkAccessToken(accessToken);
        }catch(RefreshTokenException refreshTokenException){
//            refreshTokenException.sendResponseError(response);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<>();
            map.put("msg",  refreshTokenException.getErrorCase().name());
            map.put("time", new Date());
            String responseStr = mapper.writeValueAsString(map);
            response.getWriter().println(responseStr);
            return;
        }
        log.info("1111: ");
        Map<String, Object> refreshClaims = null;
        try {
            refreshClaims = checkRefreshToken(refreshToken);
            log.info("refreshClaims: {}",refreshClaims);

        }catch(RefreshTokenException refreshTokenException){
        	log.info("333 : refreshTokenException");
//            refreshTokenException.sendResponseError(response);

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<>();
            map.put("msg", refreshTokenException.getErrorCase().name());
            map.put("time", new Date());
            String responseStr = mapper.writeValueAsString(map);
            response.getWriter().println(responseStr);
            return;
        }

        //Refresh Token의 유효시간이 얼마 남지 않은 경우
        Integer exp = (Integer)refreshClaims.get("exp");

        Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli() * 1000);

        Date current = new Date(System.currentTimeMillis());

        //만료 시간과 현재 시간의 간격 계산
        //만일 3일 미만인 경우에는 Refresh Token도 다시 생성
        long gapTime = (expTime.getTime() - current.getTime());

        log.info("-----------------------------------------");
        log.info("current: " + current);
        log.info("expTime: " + expTime);
        log.info("gap: " + gapTime );

        String id = (String)refreshClaims.get("my");

        //이상태까지 오면 무조건 AccessToken은 새로 생성
        String accessTokenValue = jwtUtil.generateToken(Map.of("my", id), 1);

        String refreshTokenValue = tokens.get("refreshToken");

        //RefrshToken이 3일도 안남았다면..
        if(gapTime < (1000 * 60  * 3  ) ){ //테스트용도로 3분도 안남았다면..
        //if(gapTime < (1000 * 60 * 60 * 24 * 3  ) ){ //3일도 안남았다면..
            log.info("new Refresh Token required...  ");
            refreshTokenValue = jwtUtil.generateToken(Map.of("my", id), 10);

        }

        log.info("Refresh Token result....................");
        log.info("accessToken: " + accessTokenValue);
        log.info("refreshToken: " + refreshTokenValue);

        sendTokens(accessTokenValue, refreshTokenValue, response);


    }
    private Map<String, String> getJSonData(HttpServletRequest request) {

        log.info("--요청전달된 JSON 데이터를 분석해서 Map으로 처리--");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonData = new HashMap<>();
        try {
            Reader reader = new InputStreamReader(request.getInputStream());
            jsonData = objectMapper.readValue(reader, Map.class);
            log.info("jsonData:{}", jsonData);
            return jsonData;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
//    private Map<String,String> parseRequestJSON(HttpServletRequest request) {
//
//        //JSON 데이터를 분석해서 mid, mpw 전달 값을 Map으로 처리
//        try(Reader reader = new InputStreamReader(request.getInputStream())){
//
//            Gson gson = new Gson();
//            Map<String,String> requestJSON = gson.fromJson(reader, Map.class);
//            return requestJSON;
//
//        }catch(Exception e){
//            log.error(e.getMessage());
//        }
//        return null;
//    }
//억세스 토큰 확인
    private void checkAccessToken(String accessToken)throws RefreshTokenException {

        try{
            jwtUtil.validateToken(accessToken);
        }catch (ExpiredJwtException expiredJwtException){
            log.info("Access Token has expired");
        }catch(Exception exception){
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_ACCESS);
        }
    }

    private Map<String, Object> checkRefreshToken(String refreshToken)throws RefreshTokenException{

        try {
            Map<String, Object> values = jwtUtil.validateToken(refreshToken);

            return values;

        }catch(ExpiredJwtException expiredJwtException){
        	 log.info("222: ");
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.OLD_REFRESH);
        }catch(Exception exception){
        	log.info("222: checkRefreshToken exception");
            exception.printStackTrace();
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }
//        return null;
    }

    private void sendTokens(String accessToken, String refreshToken, HttpServletResponse response) {


//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//        Gson gson = new Gson();
//
//        String jsonStr = gson.toJson(Map.of("accessToken", accessTokenValue,
//                "refreshToken", refreshTokenValue));
//
//        try {
//            response.getWriter().println(jsonStr);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> keyMap = Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken);

        try {
            String jsonStr = objectMapper.writeValueAsString(keyMap);
            response.getWriter().println(jsonStr);
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }
    }
}