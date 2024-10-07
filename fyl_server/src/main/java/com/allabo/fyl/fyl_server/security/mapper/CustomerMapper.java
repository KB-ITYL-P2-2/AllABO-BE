package com.allabo.fyl.fyl_server.security.mapper;

import com.allabo.fyl.fyl_server.security.vo.Customer;
import com.allabo.fyl.fyl_server.security.vo.CustomerAuth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    void insert(Customer customer);
    Customer findById(String id);
    void insert_auth(CustomerAuth auth);
}