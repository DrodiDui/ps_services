package com.kapitonau.authserver.oauth.mapper;

import com.kapitonau.authserver.model.UserModel;
import com.kapitonau.authserver.oauth.model.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserMapper {

    public CustomUserDetails mapToUserDetails(final UserModel user) {
        return CustomUserDetails.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .familyName(user.getFamilyName())
                .userStatusId(user.getUserStatusId())
                .roles(user.getRoles())
                .authorities(getUserAuthorities(user))
                .build();
    }

    private Collection<? extends GrantedAuthority> getUserAuthorities(UserModel user) {
        return user.getRoles()
                .stream()
                .flatMap(role -> role.getAuthorities().stream())
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityCode()))
                .toList();
    }

}
