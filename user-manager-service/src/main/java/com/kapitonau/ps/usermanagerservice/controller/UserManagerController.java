package com.kapitonau.ps.usermanagerservice.controller;

import com.kapitonau.ps.apirequestlib.usermanager.api.UserManagerApi;
import com.kapitonau.ps.apirequestlib.usermanager.dto.UserPostRequest;
import com.kapitonau.ps.apirequestlib.usermanager.dto.UserResponse;
import com.kapitonau.ps.usermanagerservice.service.UserManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserManagerController implements UserManagerApi {

    private final UserManagerService userManagerService;

    @Override
    public UserResponse createUser(UserPostRequest body) {
        return userManagerService.createUser(body);
    }
}
