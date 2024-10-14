package com.allabo.fyl.fyl_server.mapper;
import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFinancialsRatioMapper {
    void saveUserFinancialsRatio(UserFinancialsRatioDAO dao);
    UserFinancialsRatioDAO findById(String id);
    void updateUserFinancialRatio(UserFinancialsRatioDAO dao);
}
