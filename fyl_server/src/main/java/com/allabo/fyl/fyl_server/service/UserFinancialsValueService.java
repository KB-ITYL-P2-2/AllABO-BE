package com.allabo.fyl.fyl_server.service;
import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsValueDTO;

public interface UserFinancialsValueService {
    void processAndSaveUserFinancial(UserFinancialsValueDTO dto);

    UserFinancialsValueDTO FindUserFinancials(String id);

    //void SaveFinancialsRatio(UserFinancialsRatioDAO dao);
}
