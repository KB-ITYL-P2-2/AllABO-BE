package com.allabo.fyl.fyl_server.mapper;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFinancialsMapper {
    void insertUserFinancial(UserFinancialsDTO dto);
}
