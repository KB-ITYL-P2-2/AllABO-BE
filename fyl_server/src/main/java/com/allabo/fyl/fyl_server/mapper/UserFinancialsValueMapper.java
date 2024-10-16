package com.allabo.fyl.fyl_server.mapper;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsValueDTO;
import com.allabo.fyl.fyl_server.dto.UserPortfolioDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFinancialsValueMapper {
    void insertUserFinancialValue(UserFinancialsValueDTO dto);
    UserFinancialsValueDTO findValueById(String id);//여부확인
    void updateUserFinancialValue(UserFinancialsValueDTO dto);
}
