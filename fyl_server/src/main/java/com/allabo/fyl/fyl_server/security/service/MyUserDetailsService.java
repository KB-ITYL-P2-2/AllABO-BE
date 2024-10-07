package com.allabo.fyl.fyl_server.security.service;


import com.allabo.fyl.fyl_server.security.vo.Customer;
import com.allabo.fyl.fyl_server.security.mapper.UserDetailsMapper;
import com.allabo.fyl.fyl_server.security.vo.MyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserDetailsMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Customer vo = mapper.get(id);
        log.info(id);
        if(vo == null|| vo.getPwd() == null) {
            throw new UsernameNotFoundException(id + "은 없는 id입니다.");
        }
        log.info("loadUserByUsername vo={}", vo);
        return new MyUser(vo);
    }

}
