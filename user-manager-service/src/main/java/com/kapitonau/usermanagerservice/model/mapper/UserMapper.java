package com.kapitonau.usermanagerservice.model.mapper;

import com.kapitonau.projectstudio.event.UserAddEventDto;
import com.kapitonau.projectstudio.usermanagerservice.dto.SelfUserRegistrationPostRequest;
import com.kapitonau.projectstudio.usermanagerservice.dto.UserResponse;
import com.kapitonau.usermanagerservice.model.UserModel;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UserMapper {

    public UserModel map(SelfUserRegistrationPostRequest body){
        UserModel model = new UserModel();
        model.setEmail(body.getEmail());
        model.setUsername(body.getEmail().substring(0, body.getEmail().indexOf("@")));
        model.setFirstName(body.getFirstName());
        model.setFamilyName(body.getLastName());
        model.setCreatedDate(Instant.now());
        model.setCreatedBy(1L);
        model.setLastModifiedDate(Instant.now());
        model.setLastModifiedBy(1L);
        model.setActionType("CREATE_ENTITY");
        return model;
    }

    public UserAddEventDto mapToEventDto(UserModel model) {
        return UserAddEventDto.builder()
                .firstName(model.getFirstName())
                .lastName(model.getFamilyName())
                .email(model.getEmail())
                .build();
    }

    public UserResponse map(UserModel model) {
        return new UserResponse();
    }
}
