package com.allabo.fyl.fyl_server.repository;
import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.mapper.UserFinancialsMapper;
import com.allabo.fyl.fyl_server.mapper.UserFinancialsRatioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserFinancialsRatioRepository {
    private final UserFinancialsRatioMapper userFinancialsRatioMapper;

    public void saveUserFinancialsRatio(UserFinancialsRatioDAO dao) {
        userFinancialsRatioMapper.saveUserFinancialsRatio(dao);
    }
    public UserFinancialsRatioDAO findById(String id) {
        return userFinancialsRatioMapper.findById(id);
    }
    public void updateUserFinancialRatio(UserFinancialsRatioDAO dao){
        userFinancialsRatioMapper.updateUserFinancialRatio(dao);
    }

}
