package com.kapitonau.usermanagerservice.service;

import com.kapitonau.projectstudio.usermanagerservice.dto.SelfUserRegistrationPostRequest;
import com.kapitonau.projectstudio.usermanagerservice.dto.UserPostRequest;
import com.kapitonau.projectstudio.usermanagerservice.dto.UserResponse;

public interface UserService {
    UserResponse createUser(UserPostRequest body);

    UserResponse selfRegistration(SelfUserRegistrationPostRequest body);
}
