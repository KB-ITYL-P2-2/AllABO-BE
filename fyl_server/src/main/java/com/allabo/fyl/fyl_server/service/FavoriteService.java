package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.entity.Favorite;
import com.allabo.fyl.fyl_server.mapper.FavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    // 즐겨찾기 추가
    public void addFavorite(Long userId, Long productId, String productType) {
        favoriteMapper.insertFavorite(userId, productId, productType);
    }

    // 즐겨찾기 삭제
    public void removeFavorite(Long userId, Long productId, String productType) {
        favoriteMapper.deleteFavorite(userId, productId, productType);
    }

    // 즐겨찾기 목록 조회
    public List<Favorite> getFavorites(Long userId) {
        return favoriteMapper.getFavoritesByUserId(userId);
    }
}
