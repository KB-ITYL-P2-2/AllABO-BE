package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dto.FavoriteInsertDTO;
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
        FavoriteInsertDTO dto = new FavoriteInsertDTO(userId,productId,productType);
        favoriteMapper.insertFavorite(dto);
    }

    // 즐겨찾기 목록 조회
    public List<Favorite> getFavoritesByUserId(Long userId) {
        return favoriteMapper.selectFavoritesByUserId(userId);
    }

    // 즐겨찾기 삭제
    public void removeFavorite(Long userId, Long productId, String productType) {
        favoriteMapper.deleteFavorite(userId, productId, productType);
    }
}
