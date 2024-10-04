package com.allabo.fyl.kb_server.assets.service;

import com.allabo.fyl.kb_server.assets.dto.TotalUserAssetDTO;

public interface TotalUserAssetService {
    TotalUserAssetDTO getTotalUserAsset(int customerId);//총 저축,총 지출 from KB
}
