package com.allabo.fyl.fyl_server.service;


import com.allabo.fyl.fyl_server.dao.*;
import com.allabo.fyl.fyl_server.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImp(ProductMapper productMapper){
        this.productMapper = productMapper;
    }

    public List<CheckCardDAO> getAllCheckCard(){
            return productMapper.selectAllCheckCard();
    }
    public List<CreditCardDAO> getAllCreditCard(){
        return productMapper.selectAllCreditCard();
    };
    public List<LoanDAO> getAllLoan(){
        return productMapper.selectAllLoan();
    };
    public List<InsuranceDAO> getAllInsurance(){
        return productMapper.selectAllInsurance();
    };

    public List<DepositDAO> getAllDeposit(){
        return productMapper.selectAllDeposit();
    }
}
