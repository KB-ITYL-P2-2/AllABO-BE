package com.allabo.fyl.kb_server.assets.service;

import com.allabo.fyl.kb_server.assets.exception.NotFoundException;
import com.allabo.fyl.kb_server.assets.dto.TotalUserAssetDTO;
import com.allabo.fyl.kb_server.assets.repository.TotalUserAssetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class TotalUserAssetServiceImpl implements TotalUserAssetService {

    private final TotalUserAssetRepository totalUserAssetRepository;

    @Override
    public TotalUserAssetDTO getTotalUserAsset(int customerId) {
        // 레포지토리에서 데이터를 조회
        TotalUserAssetDTO totalUserAssetDTO = totalUserAssetRepository.findTotalUserAssetByCustomerId(customerId);

        // 데이터가 없을 경우 예외 처리
        if (totalUserAssetDTO == null) {
            log.error("TotalUserAssetDTO not found for customerId: {}", customerId);
            throw new NotFoundException(customerId);
        }

        // 데이터 반환
        return totalUserAssetDTO;
    }
}