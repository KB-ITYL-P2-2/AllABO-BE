package com.allabo.fyl.fyl_server.service;
import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;

public interface UserFinancialsRatioService {
    void saveUserFinancialsRatio(UserFinancialsRatioDAO dao);
    UserFinancialsRatioDAO findById(String id);

    void updateUserFinancialRatio(UserFinancialsRatioDAO dao);
}
