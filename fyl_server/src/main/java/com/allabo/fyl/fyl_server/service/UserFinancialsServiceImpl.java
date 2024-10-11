package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dao.UserFinancialsRatioDAO;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.repository.UserFinancialsRepository;
import com.allabo.fyl.fyl_server.security.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFinancialsServiceImpl implements UserFinancialsService{
    private final UserFinancialsRepository userFinancialsRepository;
    @Override
    public void processAndSaveUserFinancial(UserFinancialsDTO dto) {
            if (dto == null || dto.getId() == null){
                log.error("Invalid UserFinancialsDTO data: {}", dto);
                throw new IllegalArgumentException("Invalid UserFinancialsDTO data");
            }
            if (userFinancialsRepository.findUserFinancial(dto.getId()) != null) {
                userFinancialsRepository.updateUserFinancial(dto);
            } else {
                userFinancialsRepository.saveUserFinancial(dto);
            }
    }

    @Override
    public UserFinancialsDTO FindUserFinancials(String id) {
        UserFinancialsDTO dto = userFinancialsRepository.findUserFinancial(id);
        if (dto == null || id == null){
            throw new IllegalArgumentException("id가 null이거나 존재하는 financial 데이터가 없습니다.");
        }
        return dto;
    }


    @Override
    public void SaveFinancialsRatio(UserFinancialsRatioDAO dao) {
        if (dao == null || dao.getId() == null){
            throw new IllegalArgumentException("UserFinancialsRatio를 구하기 위한 id가 정상적이지 않거나 해당 객체를 찾을 수 없습니다.");
        }
        userFinancialsRepository.saveUserFinancialsRatio(dao);//save
    }



}
