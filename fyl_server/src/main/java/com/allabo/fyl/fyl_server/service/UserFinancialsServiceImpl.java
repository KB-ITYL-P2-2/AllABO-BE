package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.repository.UserFinancialsRepository;
import com.allabo.fyl.fyl_server.security.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFinancialsServiceImpl implements UserFinancialsService {
    private final UserFinancialsRepository userFinancialsRepository;

    @Override
    public void processAndSaveUserFinancial(UserFinancialsDTO dto) {
        if (dto == null || dto.getId() == null) {
            log.error("Invalid UserFinancialsDTO data: {}", dto);
            throw new IllegalArgumentException("Invalid UserFinancialsDTO data");
        }
        if (userFinancialsRepository.findUserFinancial(dto.getId()).isPresent()) {
            userFinancialsRepository.updateUserFinancial(dto);
        } else {
            userFinancialsRepository.saveUserFinancial(dto);
        }
    }

    @Override
    public UserFinancialsDTO FindUserFinancials(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return userFinancialsRepository.findUserFinancial(id)
                .orElseThrow(() -> new IllegalArgumentException("No financial data found for the given id"));
    }

    @Override
    public void SaveFinancialsRatio(UserFinancialsRatioDAO dao) {
        if (dao == null || dao.getId() == null) {
            throw new IllegalArgumentException("UserFinancialsRatio를 구하기 위한 id가 정상적이지 않거나 해당 객체를 찾을 수 없습니다.");
        }
        userFinancialsRepository.saveUserFinancialsRatio(dao); // save
    }

}
