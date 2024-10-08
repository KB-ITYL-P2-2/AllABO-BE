package com.allabo.fyl.kb_server.assets.security.mapper;

import com.allabo.fyl.kb_server.assets.dto.TotalUserAssetDTO;
import com.allabo.fyl.kb_server.assets.vo.Customer;

public interface TotalUserAssetMapper {
    TotalUserAssetDTO get(String identity);
    Customer findById(String id);
}
