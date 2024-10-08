package com.allabo.fyl.fyl_server.mapper;

import com.allabo.fyl.fyl_server.dto.FavoriteInsertDTO;
import com.allabo.fyl.fyl_server.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    // 즐겨찾기 추가
    void insertFavorite(FavoriteInsertDTO dto);

    // 사용자별 즐겨찾기 목록 조회
    List<Favorite> selectFavoritesByUserId(Long userId);

    // 즐겨찾기 삭제 (마이페이지에서 삭제 가능)
    void deleteFavorite(Long userId, Long productId,String productType);
}
