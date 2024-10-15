package com.allabo.fyl.fyl_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResetDTO {
    private String id;
    private String phoneNumber;
    private String pwd;
}
