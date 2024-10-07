package com.allabo.fyl.fyl_server.repository;
import com.allabo.fyl.fyl_server.dto.UserFinancialsDTO;
import com.allabo.fyl.fyl_server.mapper.UserFinancialsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserFinancialsRepository {
    private final UserFinancialsMapper userFinancialsMapper;
    public void saveUserFinancial(UserFinancialsDTO userFinancialDTO) {
        userFinancialsMapper.insertUserFinancial(userFinancialDTO);
    }
}
