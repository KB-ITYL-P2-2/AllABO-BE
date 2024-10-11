package com.allabo.fyl.fyl_server.mapper;

import com.allabo.fyl.fyl_server.dao.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<CheckCardDAO> selectAllCheckCard();
    List<CreditCardDAO> selectAllCreditCard();
    List<LoanDAO> selectAllLoan();
    List<InsuranceDAO> selectAllInsurance();
    List<DepositDAO> selectAllDeposit();
}
