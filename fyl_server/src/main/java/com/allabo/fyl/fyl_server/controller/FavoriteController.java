package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.entity.Favorite;
import com.allabo.fyl.fyl_server.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // 즐겨찾기 추가
    @PostMapping("/add")
    public void addFavorite(@RequestParam Long userId, @RequestParam Long productId, @RequestParam String productType) {
        favoriteService.addFavorite(userId, productId, productType);
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/remove")
    public void removeFavorite(@RequestParam Long userId, @RequestParam Long productId, @RequestParam String productType) {
        favoriteService.removeFavorite(userId, productId, productType);
    }

    // 즐겨찾기 목록 조회
    @GetMapping("/list")
    public List<Favorite> getFavorites(@RequestParam Long userId) {
        return favoriteService.getFavorites(userId);
    }
}
