package com.kapitonau.ps.authservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "client_id_issued_at", nullable = false)
    private Instant clientIdIssuedAt;

    @ColumnDefault("NULL")
    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "client_secret_expires_at")
    private Instant clientSecretExpiresAt;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "client_authentication_methods", nullable = false, length = 1000)
    private String clientAuthenticationMethods;

    @Column(name = "authorization_grant_types", nullable = false, length = 1000)
    private String authorizationGrantTypes;

    @Column(name = "scopes", nullable = false, length = 1000)
    private String scopes;

    @Column(name = "client_settings", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> clientSettings;

    @Column(name = "token_settings", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> tokenSettings;

}