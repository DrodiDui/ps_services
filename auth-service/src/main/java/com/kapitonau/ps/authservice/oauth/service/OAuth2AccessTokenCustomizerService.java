package com.kapitonau.ps.authservice.oauth.service;

import com.kapitonau.ps.authservice.model.UserModel;
import com.kapitonau.ps.authservice.oauth.OAuth2GrantPasswordAuthorizationToken;
import com.kapitonau.ps.authservice.oauth.dto.UserWorkspaceAuthorities;
import com.kapitonau.ps.authservice.repository.UserRepository;
import com.kapitonau.ps.authservice.repository.UserRoleRepository;
import com.kapitonau.ps.authservice.repository.projection.UserWorkspaceAuthoritiesProjection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class OAuth2AccessTokenCustomizerService implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    @Override
    public void customize(JwtEncodingContext context) {
        Authentication authorizationGrant = context.getAuthorizationGrant();
        OAuth2GrantPasswordAuthorizationToken passwordAuthentication =
                (OAuth2GrantPasswordAuthorizationToken) authorizationGrant;
        String principalName = passwordAuthentication.getUsername();
        UserModel userModel = userRepository.findByUsername(principalName).orElse(null);
        context.getClaims()
                .claim("userId", userModel.getUserId())
                .claim("email", userModel.getEmail())
                .claim("sub", principalName)
                .claim("workspace-authorities", getWorkspaceAuthorities(userModel.getUserId()))
                .build();
    }

    private List<UserWorkspaceAuthorities> getWorkspaceAuthorities(Long userId) {
        return userRoleRepository.findUserWorkspaceAuthoritiesByUserId(userId)
                .stream()
                .collect(Collectors.groupingBy(
                        projection -> Pair.of(projection.getRoleName(), projection.getWorkspaceId()),
                        Collectors.mapping(
                                UserWorkspaceAuthoritiesProjection::getAuthorityName,
                                Collectors.toList()
                        )
                ))
                .entrySet()
                .stream()
                .map(entry -> UserWorkspaceAuthorities.builder()
                        .roleName(entry.getKey().getFirst())
                        .workspaceId(entry.getKey().getSecond())
                        .authorities(entry.getValue())
                        .build())
                .toList();
    }

}
