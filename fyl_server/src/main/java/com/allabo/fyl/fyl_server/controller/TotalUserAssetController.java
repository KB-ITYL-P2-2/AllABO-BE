package com.allabo.fyl.fyl_server.controller;


import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
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
//    private TotalUserAssetService service;
//    @GetMapping("/{customerId}")
//    public ResponseEntity<UserFinancialsDTO> findTotalAssetByUserID(@PathVariable int customerId) {
//        UserFinancialsDTO dto = service.getTotalUserAsset(customerId);
//        if(dto==null){
//            System.out.println("controller 404");
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok().body(dto);
//    }
}
