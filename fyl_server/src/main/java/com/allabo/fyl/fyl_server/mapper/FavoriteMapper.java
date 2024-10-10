package com.allabo.fyl.fyl_server.mapper;

import com.allabo.fyl.fyl_server.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    // 즐겨찾기 추가 - 어노테이션 제거, 매퍼 XML에서 정의
    void insertFavorite(@Param("userId") Long userId, @Param("productId") Long productId, @Param("productType") String productType);

    // 즐겨찾기 삭제 - 어노테이션 제거, 매퍼 XML에서 정의
    void deleteFavorite(@Param("userId") Long userId, @Param("productId") Long productId, @Param("productType") String productType);

    // 즐겨찾기 목록 조회 - 어노테이션 제거, 매퍼 XML에서 정의
    List<Favorite> getFavoritesByUserId(Long userId);
}
