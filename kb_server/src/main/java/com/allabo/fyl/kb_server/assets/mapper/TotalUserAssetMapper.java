package com.allabo.fyl.kb_server.assets.mapper;

import com.allabo.fyl.kb_server.assets.dto.TotalUserAssetDTO;

public interface TotalUserAssetMapper {
    TotalUserAssetDTO get(int customerId);
}
