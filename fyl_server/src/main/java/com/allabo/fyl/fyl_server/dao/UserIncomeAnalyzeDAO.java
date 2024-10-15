package com.allabo.fyl.fyl_server.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIncomeAnalyzeDAO {
    private String id;
    private String results;
    private String incomeLev;
}
