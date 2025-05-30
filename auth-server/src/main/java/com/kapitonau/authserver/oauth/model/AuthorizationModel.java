package com.kapitonau.authserver.oauth.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "authorizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationModel {

    @Id
    @Column(name = "authorization_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorizations_authorization_id_seq")
    @SequenceGenerator(name = "authorizations_authorization_id_seq", sequenceName = "authorizations_authorization_id_seq", allocationSize = 1)
    private Long authorizationId;

    @Column(name = "registered_client_id")
    private Long registeredClientId;
    @Column(name = "authorization_grant_type")
    private String authorizationGrantType;
    @Column(name = "authorized_scopes")
    private String authorizedScopes;
    @Column(name = "attributes")
    private String attributes;
    @Column(name = "access_token_value")
    private String accessTokenValue;
    @Column(name = "access_token_issued_at")
    private ZonedDateTime accessTokenIssuedAt;
    @Column(name = "access_token_expires_at")
    private ZonedDateTime accessTokenExpiresAt;
    @Column(name = "access_token_metadata")
    private String accessTokenMetadata;
    @Column(name = "access_token_scopes")
    private String accessTokenScopes;
    @Column(name = "refresh_token_value")
    private String refreshTokenValue;
    @Column(name = "refresh_token_issued_at")
    private ZonedDateTime refreshTokenIssuedAt;
    @Column(name = "refresh_token_expires_at")
    private ZonedDateTime refreshTokenExpiresAt;
    @Column(name = "refresh_token_metadata")
    private String refreshTokenMetadata;
}
