package com.kapitonau.ps.usermanagerservice.service;

import com.kapitonau.ps.apirequestlib.usermanager.dto.UserPostRequest;
import com.kapitonau.ps.apirequestlib.usermanager.dto.UserResponse;
import com.kapitonau.ps.apirequestlib.workspaces.dto.MemberResponse;

public interface UserManagerService {
    MemberResponse createUser(UserPostRequest body);
}
