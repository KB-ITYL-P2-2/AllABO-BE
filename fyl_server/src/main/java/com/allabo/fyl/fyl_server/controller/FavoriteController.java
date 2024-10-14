package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.entity.Favorite;
import com.allabo.fyl.fyl_server.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // 즐겨찾기 추가
    @PostMapping("/add")
    public void addFavorite(Authentication authentication, @RequestParam String productId, @RequestParam int productNum) {
        favoriteService.addFavorite(authentication.getName(), productId, productNum);
    }

    // 즐겨찾기 삭제
    @PostMapping("/remove")
    public void removeFavorite(Authentication authentication,@RequestParam String productId, @RequestParam int productNum) {
        System.out.println("삭제 요청~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(authentication.getName() + productId + productNum);
        favoriteService.removeFavorite(authentication.getName(), productId, productNum);
    }

    // 즐겨찾기 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<Favorite>> getFavorites(Authentication authentication) {
        List<Favorite> favorites = favoriteService.getFavorites(authentication.getName());
        System.out.println(favorites);
        System.out.println(favorites.size());
        if(favorites.size() > 0) {
            System.out.println("성공 요청이야#################");
            return ResponseEntity.ok(favorites);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
}
