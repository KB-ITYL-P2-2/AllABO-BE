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
//1)로그인 후 2)토근 가지고 체크필터 단계에서 사용
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {//id from 토큰(username)
        Customer vo = mapper.get(id);
        log.info(id);
        if(vo == null|| vo.getPwd() == null) {
            throw new UsernameNotFoundException(id + "은 없는 id입니다.");
        }
        log.info("loadUserByUsername vo={}", vo);
        return new MyUser(vo);//리턴값이 authentication객체로 들어감 from JWT토근(인증완료된)
        //authentication객체 장점 : controller매개변수로 쓸 수 있음. 얘를 통해서 MyUser를 꺼내올 수있다.
        //myUser는 이미 mapper.get(id) 로 id에 해당하는 customer객체 채워 갖고있는 애
    }

}
