package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dao.*;
import com.allabo.fyl.fyl_server.service.KakaoLoginService;
import com.allabo.fyl.fyl_server.service.ProductService;
import com.allabo.fyl.fyl_server.service.ProductServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class ProductController {
    @Autowired
    private ProductServiceImp productServiceImp;

    @GetMapping("/check-card")
    public List<CheckCardDAO> requestCheckCard(){
//        System.out.println(productServiceImp.getAllCheckCard());
        return productServiceImp.getAllCheckCard();
    }

    @GetMapping("/credit-card")
    public List<CreditCardDAO> requestCreditCard(){
//        System.out.println(productServiceImp.getAllCheckCard());
        return productServiceImp.getAllCreditCard();
    }

    @GetMapping("/loan")
    public List<LoanDAO> requestLoan(){
//        System.out.println(productServiceImp.getAllCheckCard());
        return productServiceImp.getAllLoan();
    }

    @GetMapping("/insurance")
    public List<InsuranceDAO> requestInsurance(){
//        System.out.println(productServiceImp.getAllCheckCard());
        return productServiceImp.getAllInsurance();
    }

    @GetMapping("/deposit")
    public List<DepositDAO> requestDeposit(){
//        System.out.println(productServiceImp.getAllCheckCard());
        return productServiceImp.getAllDeposit();
    }
}
