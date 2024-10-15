package com.allabo.fyl.fyl_server.mapper;
import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.dto.UserPortfolioDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFinancialsMapper {
    void insertUserFinancial(UserFinancialsDTO dto);
    UserFinancialsDTO findById(String id);//여부확인
    void updateUserFinancial(UserFinancialsDTO dto);
    UserPortfolioDTO portfolioFindById(String id);
}
