package com.allabo.fyl.kb_server.assets.service;

import com.allabo.fyl.kb_server.assets.exception.NotFoundException;
import com.allabo.fyl.kb_server.assets.dto.TotalUserAssetDTO;
import com.allabo.fyl.kb_server.assets.mapper.TotalUserAssetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TotalUserAssetServiceImpl implements TotalUserAssetService {
    final TotalUserAssetMapper mapper;

    @Override
    public TotalUserAssetDTO getTotalUserAsset(int customerId) {
        TotalUserAssetDTO totalUserAssetDTO = mapper.get(customerId);
        if (totalUserAssetDTO == null) {
            System.out.println("TotalUserAssetServiceImpl error");
            throw new NotFoundException();
        }
        return totalUserAssetDTO;
    }
}
