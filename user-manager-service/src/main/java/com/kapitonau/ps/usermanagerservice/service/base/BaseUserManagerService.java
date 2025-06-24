package com.kapitonau.ps.usermanagerservice.service.base;

import com.kapitonau.ps.apirequestlib.kafka.MemberCreateEventMessage;
import com.kapitonau.ps.apirequestlib.usermanager.dto.UserPostRequest;
import com.kapitonau.ps.apirequestlib.workspaces.dto.MemberResponse;
import com.kapitonau.ps.commonspringlib.exception.CommonServiceException;
import com.kapitonau.ps.usermanagerservice.event.sender.UserCreateEventSender;
import com.kapitonau.ps.usermanagerservice.model.RoleModel;
import com.kapitonau.ps.usermanagerservice.model.UserModel;
import com.kapitonau.ps.usermanagerservice.model.mapper.UserMapper;
import com.kapitonau.ps.usermanagerservice.repository.RoleRepository;
import com.kapitonau.ps.usermanagerservice.repository.UserRepository;
import com.kapitonau.ps.usermanagerservice.repository.UserRoleRepository;
import com.kapitonau.ps.usermanagerservice.service.UserManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BaseUserManagerService implements UserManagerService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserCreateEventSender userCreateEventSender;
    private final RoleRepository roleRepository;

    @Transactional(transactionManager = "userTransactionManager")
    @Override
    public MemberResponse createUser(UserPostRequest body) {

        UserModel model = userMapper.toModel(body, passwordEncoder);
        model = userRepository.save(model);

        userCreateEventSender.send(MemberCreateEventMessage.builder()
                        .memberId(model.getUserId())
                        .username(model.getUsername())
                        .email(model.getEmail())
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .middleName(body.middleName())
                        .memberPositionCode(body.memberPosition())
                        .createDate(model.getCreatedDate())
                        .createdBy(model.getCreatedBy())
                        .lastModifiedDate(model.getLastModifyingDate())
                        .lastModifiedBy(model.getLastModifyingBy())
                        .roleId(getMaintainerRole())
                .build());

        return userMapper.toResponse(model, body.memberPosition());
    }

    private Long getMaintainerRole() {
        RoleModel model = roleRepository.findByRoleCode("MAINTAINER")
                .orElseThrow(() -> new CommonServiceException("USER_MANAGER_SERVICE", "Role not found"));

        return model.getRoleId();
    }
}
