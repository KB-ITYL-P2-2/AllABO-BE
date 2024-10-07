package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dto.KBTotalDTO;
import com.allabo.fyl.fyl_server.security.vo.Customer;

public interface UserFinancialsService {
    void processAndSaveUserFinancial(Customer customer, KBTotalDTO kbTotal);
}
