package com.kapitonau.ps.authservice.oauth.service;

import com.kapitonau.ps.authservice.exception.OAuth2AuthorizationServerException;
import com.kapitonau.ps.authservice.model.RoleModel;
import com.kapitonau.ps.authservice.model.UserModel;
import com.kapitonau.ps.authservice.model.UsersRoleModel;
import com.kapitonau.ps.authservice.oauth.CustomUserDetails;
import com.kapitonau.ps.authservice.repository.AuthorityRepository;
import com.kapitonau.ps.authservice.repository.RoleRepository;
import com.kapitonau.ps.authservice.repository.UserRepository;
import com.kapitonau.ps.authservice.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MessageSource messageSource;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new OAuth2AuthorizationServerException(
                        "AUTH_SERVER_ERROR_1",
                        messageSource.getMessage("AUTH_SERVER_ERROR_1", null, getLocale())
                ));

        if (!user.getIsActive()) {
            throw new OAuth2AuthorizationServerException(
                    "AUTH_SERVER_ERROR_3",
                    messageSource.getMessage("AUTH_SERVER_ERROR_3", null, getLocale()
                    ));
        }

        List<RoleModel> userRoles = roleRepository.findAllByUserId(user.getUserId());

        if (userRoles == null || userRoles.isEmpty()) {
            throw new OAuth2AuthorizationServerException(
                    "AUTH_SERVER_ERROR_4",
                    messageSource.getMessage("AUTH_SERVER_ERROR_4", null, getLocale()
                    ));
        }

        return CustomUserDetails.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.getIsActive())
                .roles(userRoles)
                .authorities(getUserAuthorities(userRoles))
                .build();
    }

    private List<RoleModel> getUserRoles(List<UsersRoleModel> userRoles) {
        Set<Long> rolesId = userRoles.stream()
                .map(it -> it.getUserRoleId().getRoleId())
                .collect(Collectors.toSet());
        return roleRepository.findAllById(rolesId);
    }

    private Collection<? extends GrantedAuthority> getUserAuthorities(List<RoleModel> roles) {
        Set<Long> roleIds = roles.stream().map(RoleModel::getRoleId).collect(Collectors.toSet());

        return authorityRepository.findAllByRoleIds(roleIds)
                .stream()
                .map(it -> new SimpleGrantedAuthority(it.getAuthorityCode()))
                .collect(Collectors.toSet());
    }
}
