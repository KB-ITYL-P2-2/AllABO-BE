package com.allabo.fyl.kb_server.assets.controller;

import com.allabo.fyl.kb_server.assets.security.util.JWTUtil;
import com.allabo.fyl.kb_server.assets.dto.TotalUserAssetDTO;
import com.allabo.fyl.kb_server.assets.service.TotalUserAssetService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kb")
public class TotalUserAssetController {
    private final JWTUtil jwtUtil;
    private final TotalUserAssetService service;

    @GetMapping("/total")
    public ResponseEntity<?> getTotalUserAsset(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // "Bearer " 이후의 토큰 추출

        // JWT 토큰에서 클레임 추출
        Claims claims = jwtUtil.getClaimsFromToken(token);
        String identityNumber = claims.get("identityNumber", String.class); // 클레임에서 identityNumber 추출

        log.info("controller value:", identityNumber);
        TotalUserAssetDTO dto = service.getTotalUserAsset(identityNumber);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
        return ResponseEntity.ok(dto); // 200 OK
    }
}
