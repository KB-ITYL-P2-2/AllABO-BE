package com.allabo.fyl.fyl_server.security.vo;

import com.allabo.fyl.fyl_server.security.vo.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class MyUser extends User {
    private Customer customer;		// 실질적인 사용자 데이터

    public MyUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MyUser(Customer vo) {
        super(vo.getId(), vo.getPwd(), vo.getAuths());
        this.customer = vo;
    }

}