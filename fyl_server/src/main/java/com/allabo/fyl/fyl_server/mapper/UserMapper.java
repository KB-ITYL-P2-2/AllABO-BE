package com.allabo.fyl.fyl_server.mapper;

import com.allabo.fyl.fyl_server.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    // SQL 쿼리로 사용자 정보 조회
//    @Select("SELECT id as userId, name as name, phone_number as phoneNumber FROM customer WHERE id = #{userId}")
    UserDTO selectUserProfile(@Param("userId") String userId);
}
