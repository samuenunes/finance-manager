package com.leumas.finance.service;

import com.leumas.finance.controller.request.UserRequest;
import com.leumas.finance.controller.response.UserResponse;
import com.leumas.finance.entity.User;
import com.leumas.finance.mapper.UserMapper;
import com.leumas.finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest) {
        User newUser = UserMapper.toUser(userRequest);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return UserMapper.toUserResponse(userRepository.save(newUser));
    }
}
