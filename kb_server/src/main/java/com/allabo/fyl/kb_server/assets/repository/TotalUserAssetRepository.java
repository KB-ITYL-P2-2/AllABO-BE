package com.allabo.fyl.kb_server.assets.repository;

import com.allabo.fyl.kb_server.assets.dto.TotalUserAssetDTO;
import com.allabo.fyl.kb_server.assets.security.mapper.TotalUserAssetMapper;
import com.allabo.fyl.kb_server.assets.vo.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TotalUserAssetRepository {
    private final TotalUserAssetMapper totalUserAssetMapper;
    public TotalUserAssetDTO findTotalUserAssetByCustomerId(String identity) {
        return totalUserAssetMapper.get(identity);
    }
    public Customer findUser(String identity) {
        return totalUserAssetMapper.findById(identity);
    }
}
