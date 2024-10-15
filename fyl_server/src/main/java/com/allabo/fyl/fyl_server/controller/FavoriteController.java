package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dto.FavoriteProductDTO;
import com.allabo.fyl.fyl_server.entity.Favorite;
import com.allabo.fyl.fyl_server.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin
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
    public void removeFavorite(Authentication authentication, @RequestParam String productId, @RequestParam int productNum) {
        favoriteService.removeFavorite(authentication.getName(), productId, productNum);
    }

    // 즐겨찾기 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<Favorite>> getFavorites(Authentication authentication) {
        List<Favorite> favorites = favoriteService.getFavoritesByUserId(authentication.getName());
        System.out.println(favorites);
        System.out.println(favorites.size());
        if (favorites.size() > 0) {
            System.out.println("성공 요청이야#################");
            return ResponseEntity.ok(favorites);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // 즐겨찾기 목록 조회 (상품 정보 포함)
    @GetMapping("/mapping-list")
    public ResponseEntity<List<FavoriteProductDTO>> getFavoritesWithProducts(Authentication authentication) {
        System.out.println("/api/favorites/list ~~~ #################################");
        System.out.println(authentication.getName());
        List<FavoriteProductDTO> favorites = favoriteService.getFavorites(authentication.getName());
        if (favorites != null && !favorites.isEmpty()) {
            return ResponseEntity.ok(favorites);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
