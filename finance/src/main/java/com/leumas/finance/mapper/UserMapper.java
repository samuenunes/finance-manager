package com.leumas.finance.mapper;

import com.leumas.finance.controller.request.UserRequest;
import com.leumas.finance.controller.response.UserResponse;
import com.leumas.finance.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static User toUser(UserRequest request) {
        return User
                .builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .role(request.role())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
