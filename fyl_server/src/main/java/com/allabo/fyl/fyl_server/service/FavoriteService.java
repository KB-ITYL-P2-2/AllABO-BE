package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dto.FavoriteProductDTO;
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
    public void addFavorite(String userId, String productId, int productNum) {
        favoriteMapper.addFavorite(userId, productId, productNum);
    }

    // 즐겨찾기 삭제
    public void removeFavorite(String userId, String productId, int productNum) {
        favoriteMapper.removeFavorite(userId, productId, productNum);
    }

    // 즐겨찾기 목록 조회 및 상품 정보 포함
    public List<FavoriteProductDTO> getFavorites(String userId) {
        return favoriteMapper.getFavorites(userId);
    }
}
