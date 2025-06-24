package com.kapitonau.ps.usermanagerservice.model.mapper;

import com.kapitonau.ps.apirequestlib.bean.cache.ReferenceCache;
import com.kapitonau.ps.apirequestlib.usermanager.dto.UserPostRequest;
import com.kapitonau.ps.apirequestlib.workspaces.dto.MemberResponse;
import com.kapitonau.ps.usermanagerservice.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ReferenceCache referenceCache;


    public UserModel toModel(UserPostRequest userPostRequest, PasswordEncoder passwordEncoder) {
        UserModel model = new UserModel();
        model.setUsername(userPostRequest.username());
        model.setEmail(userPostRequest.email());
        model.setPassword(passwordEncoder.encode(userPostRequest.password()));
        model.setFirstName(userPostRequest.firstName());
        model.setLastName(userPostRequest.lastName());
        model.setIsActive(true);
        model.setActivationCode(UUID.randomUUID().toString());
        model.setCreatedDate(ZonedDateTime.now());
        model.setCreatedBy(0L);
        model.setLastModifyingDate(ZonedDateTime.now());
        model.setLastModifyingBy(0L);

        return model;
    }

    public MemberResponse toResponse(UserModel model, String memberPosition) {
        return MemberResponse.builder()
                .memberId(model.getUserId())
                .email(model.getEmail())
                .username(model.getUsername())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .memberPosition(referenceCache.getReferenceItemByTypeAndCode("MEMBER_POSITION", memberPosition))
                .memberStatus(referenceCache.getReferenceItemByTypeAndCode("MEMBER_STATUS", "ACTIVE"))
                .build();
    }

}
