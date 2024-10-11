package com.allabo.fyl.fyl_server.security.dto;

import com.allabo.fyl.fyl_server.security.vo.Customer;
import com.allabo.fyl.fyl_server.security.vo.CustomerAuth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    String id;
    String identityNumber;
    List<String> roles;

    public static UserInfoDTO of(Customer customer) {
        return new UserInfoDTO(customer.getId(), customer.getIdentityNumber(),
                customer.getAuths().stream()
                        .map(a-> a.getAuth())
                        .toList());
    }
}
