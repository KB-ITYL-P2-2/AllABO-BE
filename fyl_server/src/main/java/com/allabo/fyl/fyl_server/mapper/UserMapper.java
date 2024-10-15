package com.allabo.fyl.fyl_server.mapper;

import com.allabo.fyl.fyl_server.dto.UserDTO;
import com.allabo.fyl.fyl_server.dto.UserResetDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    // SQL 쿼리로 사용자 정보 조회
//    @Select("SELECT id as userId, name as name, phone_number as phoneNumber FROM customer WHERE id = #{userId}")
    UserDTO selectUserProfile(@Param("userId") String userId);

//    @Update("UPDATE customer SET name=#{name}, identity_number=#{identityNumber}, phone_number=#{phoneNumber} WHERE id=#{id}")
    void updateUserProfile(UserDTO userDto);
    void updateUserPassword(@Param("id") String id, @Param("pwd") String newPassword);
}
