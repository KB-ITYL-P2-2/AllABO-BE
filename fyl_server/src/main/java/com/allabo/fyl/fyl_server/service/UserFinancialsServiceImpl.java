package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.repository.UserFinancialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFinancialsServiceImpl implements UserFinancialsService{
    private final UserFinancialsRepository userFinancialsRepository;
    @Override
    public boolean processAndSaveUserFinancial(UserFinancialsDTO dto) {
            if (dto == null || dto.getId() == null){
                log.error("Invalid UserFinancialsDTO data: {}", dto);
                throw new IllegalArgumentException("Invalid UserFinancialsDTO data");
            }
            if (userFinancialsRepository.findUserFinancial(dto.getId()) != null) {
                userFinancialsRepository.updateUserFinancial(dto);
                return true; // 업데이트 성공 시 true 반환
            } else {
                userFinancialsRepository.saveUserFinancial(dto);
                return true; // 저장 성공 시 true 반환
            }
    }
}
