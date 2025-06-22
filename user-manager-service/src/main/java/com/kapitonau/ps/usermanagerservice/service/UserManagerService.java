package com.kapitonau.ps.usermanagerservice.service;

import com.kapitonau.ps.apirequestlib.usermanager.dto.UserPostRequest;
import com.kapitonau.ps.apirequestlib.usermanager.dto.UserResponse;

public interface UserManagerService {
    UserResponse createUser(UserPostRequest body);
}
