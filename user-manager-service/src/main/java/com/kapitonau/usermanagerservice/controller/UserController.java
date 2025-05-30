package com.kapitonau.usermanagerservice.controller;

import com.kapitonau.projectstudio.usermanagerservice.api.UserApi;
import com.kapitonau.projectstudio.usermanagerservice.dto.SelfUserRegistrationPostRequest;
import com.kapitonau.projectstudio.usermanagerservice.dto.UserPostRequest;
import com.kapitonau.projectstudio.usermanagerservice.dto.UserResponse;
import com.kapitonau.usermanagerservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public UserResponse createUser(UserPostRequest body) {
        return userService.createUser(body);
    }

    @Override
    public UserResponse selfRegistration(SelfUserRegistrationPostRequest body) {
        return userService.selfRegistration(body);
    }
}
