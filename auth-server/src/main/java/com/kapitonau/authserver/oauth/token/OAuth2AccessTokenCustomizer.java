package com.kapitonau.authserver.oauth.token;

import com.kapitonau.authserver.model.*;
import com.kapitonau.authserver.oauth.common.OAuth2GrantPasswordAuthorizationToken;
import com.kapitonau.authserver.repository.UserRepository;
import com.kapitonau.authserver.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@Component
public class OAuth2AccessTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public OAuth2AccessTokenCustomizer(
            UserRepository userRepository,
            UserRoleRepository userRoleRepository
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void customize(JwtEncodingContext context) {
        Authentication authorizationGrant = context.getAuthorizationGrant();
        OAuth2GrantPasswordAuthorizationToken passwordAuthentication =
                (OAuth2GrantPasswordAuthorizationToken) authorizationGrant;
        String principalName = passwordAuthentication.getUsername();
        UserModel userModel = userRepository.findByUsername(principalName).orElse(null);
        RegisteredClient registeredClient = context.getRegisteredClient();
        context.getClaims()
                .claim("userId", userModel.getUserId())
                .claim("email", userModel.getEmail())
                .claim("sub", principalName)
                .claim("spaceAuthorities", getSpaceAuthority(userModel))
                .claim("roles", getRoles(userModel))
                .build();
    }

    private List<SpaceUserAuthority> getSpaceAuthority(UserModel userModel) {
        List<SpaceUserAuthority> spaceUserAuthorities = new ArrayList<>();
        for (RoleModel role : userModel.getRoles()) {
            Long spaceId = userRoleRepository.findById(new UserRoleId(userModel.getUserId(), role.getRoleId()))
                    .map(UserRoleModel::getSpaceId)
                    .orElse(null);
            List<String> authorities = new ArrayList<>();
            for (AuthorityModel authority : role.getAuthorities()) {
                authorities.add(authority.getAuthorityCode());
            }
            spaceUserAuthorities.add(new SpaceUserAuthority(spaceId, authorities));
        }

        return spaceUserAuthorities;
    }

    private List<String> getRoles(UserModel userModel) {
        return userModel.getRoles().stream()
                .map(RoleModel::getRoleCode)
                .toList();
    }

    private String[] getAuthority(UserModel userModel) {
        List<String> authorities = new ArrayList<>();
        for (RoleModel role : userModel.getRoles()) {
            for (AuthorityModel authority : role.getAuthorities()) {
                authorities.add(authority.getAuthorityCode());
            }
        }
        return authorities.toArray(new String[0]);
    }


    private List<Long> getUserSpaces(UserModel userModel) {

        List<UserRoleId> userRoleIds = userModel.getRoles().stream()
                .map(it -> new UserRoleId(it.getRoleId(), userModel.getUserId()))
                .toList();

        return userRoleRepository.findAllByUserRoleIdIn(userRoleIds)
                .stream()
                .map(UserRoleModel::getSpaceId)
                .toList();
    }



    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class SpaceUserAuthority {

        private Long spaceId;
        private List<String> authorities;

    }
}
