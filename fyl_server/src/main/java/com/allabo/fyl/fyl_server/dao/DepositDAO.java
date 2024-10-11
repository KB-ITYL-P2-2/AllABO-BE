package com.allabo.fyl.fyl_server.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositDAO {
    private int depositId;
    private String productId;
    private String depositName;
    private String depositContent;
    private String depositCategory;
    private String depositUrl;
    private String depositInterestRate;
    private int depositMinAmount;
    private boolean depositOnline;
    private int depositDuration;
    private boolean depositPreferInterestRate;
}
