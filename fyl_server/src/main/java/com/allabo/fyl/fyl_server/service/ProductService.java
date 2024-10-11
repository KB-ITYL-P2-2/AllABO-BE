package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dao.*;

import java.util.List;

public interface ProductService {
    List<CheckCardDAO> getAllCheckCard();
    List<CreditCardDAO> getAllCreditCard();
    List<LoanDAO> getAllLoan();
    List<InsuranceDAO> getAllInsurance();
    List<DepositDAO> getAllDeposit();
}
