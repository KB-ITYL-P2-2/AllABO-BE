package com.allabo.fyl.fyl_server.service;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.dto.UserPortfolioDTO;

public interface UserFinancialsService {
    void processAndSaveUserFinancial(UserFinancialsDTO dto);

    UserFinancialsDTO FindUserFinancials(String id);
    UserPortfolioDTO FindUserPortfolio(String id);

    void updateUserFinancial(UserFinancialsDTO dto);
}
