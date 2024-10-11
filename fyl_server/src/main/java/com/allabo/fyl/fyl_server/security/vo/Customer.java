package com.allabo.fyl.fyl_server.security.vo;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private String id;
    private String pwd;
    private String name;
    private String identityNumber;
    private String phoneNumber;
    private int totalIncome;
    private List<CustomerAuth> auths;
    private String sns = "normal";
}
