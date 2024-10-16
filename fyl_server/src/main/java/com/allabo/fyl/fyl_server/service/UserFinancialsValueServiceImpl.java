package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dto.UserFinancialsValueDTO;
import com.allabo.fyl.fyl_server.repository.UserFinancialsValueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFinancialsValueServiceImpl implements UserFinancialsValueService {
    private final UserFinancialsValueRepository userFinancialsValueRepository;

    @Override
    public void processAndSaveUserFinancial(UserFinancialsValueDTO dto) {
        if (dto == null || dto.getId() == null) {
            log.error("Invalid UserFinancialsDTO data: {}", dto);
            throw new IllegalArgumentException("Invalid UserFinancialsDTO data");
        }
        if (userFinancialsValueRepository.findUserFinancial(dto.getId()).isPresent()) {
            userFinancialsValueRepository.updateUserFinancialValue(dto);
        } else {
            userFinancialsValueRepository.saveUserFinancial(dto);
        }
    }

    @Override
    public UserFinancialsValueDTO FindUserFinancials(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return userFinancialsValueRepository.findUserFinancial(id)
                .orElseThrow(() -> new IllegalArgumentException("No financial data found for the given id"));
    }
//
//    @Override
//    public void SaveFinancialsRatio(UserFinancialsRatioDAO dao) {
//        if (dao == null || dao.getId() == null) {
//            throw new IllegalArgumentException("UserFinancialsRatio를 구하기 위한 id가 정상적이지 않거나 해당 객체를 찾을 수 없습니다.");
//        }
//        userFinancialsValueRepository.saveUserFinancial(dao); // save
//    }
}