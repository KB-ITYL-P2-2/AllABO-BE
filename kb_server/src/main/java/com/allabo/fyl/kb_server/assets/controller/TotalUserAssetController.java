package com.allabo.fyl.kb_server.assets.controller;


import com.allabo.fyl.kb_server.assets.dto.TotalUserAssetDTO;
import com.allabo.fyl.kb_server.assets.service.TotalUserAssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kb/total")
public class TotalUserAssetController {
    final TotalUserAssetService service;
    @GetMapping("/{customerId}")
    public ResponseEntity<TotalUserAssetDTO> findTotalAssetByUserID(@PathVariable int customerId) {
        TotalUserAssetDTO dto = service.getTotalUserAsset(customerId);
        if(dto==null){
            return ResponseEntity.notFound().build();//400
        }
        return ResponseEntity.ok().body(dto);//200
    }
}
