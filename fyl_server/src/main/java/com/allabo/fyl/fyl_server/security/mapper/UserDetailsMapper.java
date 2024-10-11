package com.allabo.fyl.fyl_server.security.mapper;

import com.allabo.fyl.fyl_server.security.vo.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailsMapper {
    Customer get(String id);
}
