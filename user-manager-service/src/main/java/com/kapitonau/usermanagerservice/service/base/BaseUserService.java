package com.kapitonau.usermanagerservice.service.base;

import com.kapitonau.commonspring.exception.CommonServiceException;
import com.kapitonau.projectstudio.event.UserAddEventDto;
import com.kapitonau.projectstudio.usermanagerservice.dto.SelfUserRegistrationPostRequest;
import com.kapitonau.projectstudio.usermanagerservice.dto.UserPostRequest;
import com.kapitonau.projectstudio.usermanagerservice.dto.UserResponse;
import com.kapitonau.usermanagerservice.event.sender.UserAddEventSender;
import com.kapitonau.usermanagerservice.model.UserModel;
import com.kapitonau.usermanagerservice.model.UserStatusModel;
import com.kapitonau.usermanagerservice.model.mapper.UserMapper;
import com.kapitonau.usermanagerservice.repository.UserRepository;
import com.kapitonau.usermanagerservice.repository.UserStatusRepository;
import com.kapitonau.usermanagerservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseUserService implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserStatusRepository statusRepository;
    private final UserAddEventSender userAddEventSender;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserPostRequest body) {
        return null;
    }

    @Override
    @Transactional(transactionManager = "userManagerTransaction")
    public UserResponse selfRegistration(SelfUserRegistrationPostRequest body) {

        if (userRepository.findByEmail(body.getEmail()).isPresent()) {
            throw new CommonServiceException("USER-MANAGER-SERVICE", "User already exists");
        }

        UserModel model = userMapper.map(body);

        UserStatusModel status = statusRepository.findByStatusName("ACTIVE")
                .orElse(null);

        model.setPassword(passwordEncoder.encode(body.getPassword()));
        model.setUserStatusId(status == null ? 100000 : status.getId());

        model = userRepository.save(model);

        UserAddEventDto userAddEventDto = userMapper.mapToEventDto(model);
        userAddEventDto.setUserRole(body.getRoleName());
        userAddEventSender.send(userAddEventDto);

        return userMapper.map(model);
    }
}
