package com.allabo.fyl.fyl_server.security.service;

import com.allabo.fyl.fyl_server.exception.AddException;
import com.allabo.fyl.fyl_server.exception.FindException;
import com.allabo.fyl.fyl_server.exception.NotFoundException;
import com.allabo.fyl.fyl_server.security.mapper.CustomerMapper;
import com.allabo.fyl.fyl_server.security.vo.Customer;
import com.allabo.fyl.fyl_server.security.vo.CustomerAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CustomerServiceImp implements CustomerService {
    //    private SqlSessionTemplate sqlSessionTemplate;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public CustomerServiceImp(CustomerMapper customerMapper, PasswordEncoder passwordEncoder){
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 고객을 가입한다
     * @param customer
     */
    @Transactional
    public void signup(Customer customer) throws AddException {
        try {
            if(customerMapper.findById(customer.getId())!=null){
                throw new NotFoundException();
            }
            String encodedPassword = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(encodedPassword);
            String encodedIdentity = passwordEncoder.encode(customer.getIdentityNumber());
            customer.setIdentityNumber(encodedIdentity);
            String encodedPhoneNumber = passwordEncoder.encode(customer.getPhoneNumber());
            customer.setPhoneNumber(encodedPhoneNumber);
            customerMapper.insert(customer);
            CustomerAuth customerAuth = new CustomerAuth();
            customerAuth.setId(customer.getId());  // 고객 ID 설정

            customerAuth.setAuth("ROLE_USER");  // 기본 권한 설정
            customerMapper.insert_auth(customerAuth);  // 권한 저장
        } catch (FindException e) {
            // 404로 처리
            throw e; // 그대로 예외 던짐
        } catch (Exception e) {
            // 500 에러
            e.printStackTrace();
            throw new AddException("Error occurred during signup");
        }
    }

    public void login(String id, String pwd) throws FindException {
        try {
            Customer c = customerMapper.findById(id);
            if(!c.getPwd().equals(pwd)){
                throw new FindException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException();
        }
    }

    public Customer showMyInfo(String id) throws FindException{
        try {
            Customer c = customerMapper.findById(id);
            if(c == null){
                throw new FindException();
            }
            return c;
        }catch (Exception e){
            e.printStackTrace();
            throw new FindException();
        }
    }


    public void modify(Customer customer) {}

    public void drop(Customer customer) {}
}
