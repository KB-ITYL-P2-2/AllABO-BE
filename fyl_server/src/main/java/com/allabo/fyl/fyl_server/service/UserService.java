package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dto.UserDTO;
import com.allabo.fyl.fyl_server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public UserDTO getUserProfile(String userId) {

        return userMapper.selectUserProfile(userId);
    }
    public void updateUserProfile(UserDTO userDto) {
        userMapper.updateUserProfile(userDto);
    }

}
