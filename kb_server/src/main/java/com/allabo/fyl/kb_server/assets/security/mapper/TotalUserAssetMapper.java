package com.allabo.fyl.kb_server.assets.security.mapper;

import com.allabo.fyl.kb_server.assets.dto.TotalUserAssetDTO;
import com.allabo.fyl.kb_server.assets.vo.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TotalUserAssetMapper {
    TotalUserAssetDTO get(String identity);
    Customer findById(String id);
}
