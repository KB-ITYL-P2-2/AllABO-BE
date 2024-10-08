package com.allabo.fyl.kb_server.assets.security.exception;


//import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Getter
public class RefreshTokenException extends RuntimeException{

    private ErrorCase errorCase;

    public enum ErrorCase {
        NO_ACCESS, NO_REFRESH, OLD_REFRESH
    }

    public RefreshTokenException(ErrorCase errorCase){
        super(errorCase.name());
        this.errorCase = errorCase;
    }

    public void sendResponseError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("msg", errorCase.name());
        map.put("time", new Date());
        String responseStr = mapper.writeValueAsString(map);
        response.getWriter().println(responseStr);

//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//        Gson gson = new Gson();
//
//        String responseStr = gson.toJson(Map.of("msg", errorCase.name(), "time", new Date()));
//
//        try {
//            response.getWriter().println(responseStr);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
