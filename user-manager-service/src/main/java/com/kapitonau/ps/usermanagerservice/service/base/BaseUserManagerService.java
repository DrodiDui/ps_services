package com.kapitonau.ps.usermanagerservice.service.base;

import com.kapitonau.ps.apirequestlib.usermanager.dto.UserPostRequest;
import com.kapitonau.ps.apirequestlib.usermanager.dto.UserResponse;
import com.kapitonau.ps.usermanagerservice.service.UserManagerService;
import org.springframework.stereotype.Service;

@Service
public class BaseUserManagerService implements UserManagerService {
    @Override
    public UserResponse createUser(UserPostRequest body) {
        return null;
    }
}
