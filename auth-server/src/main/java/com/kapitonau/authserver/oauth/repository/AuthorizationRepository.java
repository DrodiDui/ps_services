package com.kapitonau.authserver.oauth.repository;

import com.kapitonau.authserver.model.AuthorityModel;
import com.kapitonau.authserver.oauth.model.AuthorizationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorizationRepository extends JpaRepository<AuthorizationModel, Long> {

    Optional<AuthorizationModel> findByAccessTokenValue(String accessTokenValue);

    Optional<AuthorizationModel> findByRefreshTokenValue(String refreshTokenValue);

}
