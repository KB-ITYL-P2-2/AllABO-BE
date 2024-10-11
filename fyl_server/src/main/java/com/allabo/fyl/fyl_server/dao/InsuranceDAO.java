package com.allabo.fyl.fyl_server.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceDAO {
    private int insuranceId;
    private String productId;
    private String insuranceName;
    private String insuranceUrl;
    private String insuranceKeyword;
    private String insuranceType;
}
