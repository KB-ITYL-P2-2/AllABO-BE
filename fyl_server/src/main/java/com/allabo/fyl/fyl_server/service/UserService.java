package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dto.UserDTO;
import com.allabo.fyl.fyl_server.dto.UserResetDTO;
import com.allabo.fyl.fyl_server.exception.InvalidInputException;
import com.allabo.fyl.fyl_server.exception.PhoneNumberMismatchException;
import com.allabo.fyl.fyl_server.exception.UserNotFoundException;
import com.allabo.fyl.fyl_server.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void resetUserPassword(String userId, String newPassword) {
        UserDTO userProfile = userMapper.selectUserProfile(userId);
        if (userProfile == null) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        userMapper.updateUserPassword(userId, encodedPassword);
    }

    public UserDTO getUserProfile(String userId) {
        return userMapper.selectUserProfile(userId);
    }
    public void updateUserProfile(UserDTO userDto) {
        userMapper.updateUserProfile(userDto);
    }


}
