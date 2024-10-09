package com.allabo.fyl.fyl_server.service;
import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;

public interface UserFinancialsService {
    void processAndSaveUserFinancial(UserFinancialsDTO dto);

    UserFinancialsDTO FindUserFinancials(String id);

    void SaveFinancialsRatio(UserFinancialsRatioDAO dao);
}
