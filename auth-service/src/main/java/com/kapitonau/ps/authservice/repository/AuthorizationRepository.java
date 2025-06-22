package com.kapitonau.ps.authservice.repository;

import com.kapitonau.ps.authservice.model.AuthorizationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorizationRepository extends JpaRepository<AuthorizationModel, String> {

    Optional<AuthorizationModel> findByAccessTokenValue(String accessTokenValue);

    Optional<AuthorizationModel> findByRefreshTokenValue(String refreshTokenValue);

}
