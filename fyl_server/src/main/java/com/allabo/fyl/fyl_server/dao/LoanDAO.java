package com.allabo.fyl.fyl_server.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDAO {
    private int loanId;
    private String productId;
    private String loanName;
    private String loanUrl;
    private String loanContent;
    private String loanChannel;
    private String loanAmount;
    private String loanKeyword;
    private String loanType;
}
