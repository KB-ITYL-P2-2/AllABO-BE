package com.allabo.fyl.fyl_server.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckCardDAO {
    private int checkCardId;
    private String productId;
    private String checkCardName;
    private String checkCardContent;
    private String checkCardDetailUrl;
    private String checkCardImageUrl;
    private String checkCardCategory;
}
