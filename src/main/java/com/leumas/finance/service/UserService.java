package com.leumas.finance.service;

import com.leumas.finance.controller.request.UserRequest;
import com.leumas.finance.controller.response.UserResponse;
import com.leumas.finance.admin.entity.User;
import com.leumas.finance.entity.enums.Role;
import com.leumas.finance.mapper.UserMapper;
import com.leumas.finance.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SchemaService schemaService;
    private final MigrationService migrationService;

    public UserResponse createUser(UserRequest userRequest) {
        User newUser = UserMapper.toUser(userRequest);
        newUser.setRole(Role.USER);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser = userRepository.save(newUser);
        return UserMapper.toUserResponse(newUser);
    }
}