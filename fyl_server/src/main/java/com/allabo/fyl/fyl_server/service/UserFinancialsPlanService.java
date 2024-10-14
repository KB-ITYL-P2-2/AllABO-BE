package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;

public interface UserFinancialsPlanService {
    UserFinancialsDTO FindUserFinancials(String username);
}
