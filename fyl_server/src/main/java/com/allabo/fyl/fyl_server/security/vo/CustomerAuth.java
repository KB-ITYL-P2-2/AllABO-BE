package com.allabo.fyl.fyl_server.security.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Setter
@Getter
@ToString
public class CustomerAuth implements GrantedAuthority {
    private String id;
    private String auth;

    @Override
    public String getAuthority() {
        return auth;
    }
}
