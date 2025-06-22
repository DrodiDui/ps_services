package com.kapitonau.ps.authservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "authorizations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationModel {
    @Id
    @Column(name = "authorization_id", nullable = false)
    private String authorizationId;

    @Column(name = "registered_client_id", nullable = false)
    private Long registeredClientId;

    @Column(name = "authorization_grant_type", nullable = false)
    private String authorizationGrantType;

    @ColumnDefault("NULL")
    @Column(name = "authorized_scopes", length = 1000)
    private String authorizedScopes;

    @ColumnDefault("NULL")
    @Column(name = "attributes", length = 4000)
    private String attributes;

    @ColumnDefault("NULL")
    @Column(name = "access_token_value", length = 4000)
    private String accessTokenValue;

    @Column(name = "access_token_issued_at")
    private ZonedDateTime accessTokenIssuedAt;

    @Column(name = "access_token_expires_at")
    private ZonedDateTime accessTokenExpiresAt;

    @ColumnDefault("NULL")
    @Column(name = "access_token_metadata", length = 2000)
    private String accessTokenMetadata;

    @ColumnDefault("NULL")
    @Column(name = "access_token_scopes", length = 1000)
    private String accessTokenScopes;

    @ColumnDefault("NULL")
    @Column(name = "refresh_token_value", length = 4000)
    private String refreshTokenValue;

    @Column(name = "refresh_token_issued_at")
    private ZonedDateTime refreshTokenIssuedAt;

    @Column(name = "refresh_token_expires_at")
    private ZonedDateTime refreshTokenExpiresAt;

    @ColumnDefault("NULL")
    @Column(name = "refresh_token_metadata", length = 2000)
    private String refreshTokenMetadata;

}