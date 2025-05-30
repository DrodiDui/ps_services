package com.kapitonau.authserver.oauth.model;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.ZonedDateTime;
import java.util.Map;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisteredClientModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_id_seq")
    @SequenceGenerator(name = "clients_id_seq", sequenceName = "clients_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_id_issued_at")
    private ZonedDateTime clientIdIssuedAt;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "client_secret_expires_at")
    private ZonedDateTime clientSecretExpiresAt;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_authentication_methods")
    private String clientAuthenticationMethods;

    @Column(name = "authorization_grant_types")
    private String authorizationGrantTypes;

    @Column(name = "scopes")
    private String scopes;

    @Type(JsonBinaryType.class)
    @Column(name = "client_settings", columnDefinition = "jsonb")
    private Map<String, Object> clientSettings;

    @Type(JsonBinaryType.class)
    @Column(name = "token_settings", columnDefinition = "jsonb")
    private Map<String, Object> tokenSettings;

    /*@Column(name = "client_settings")
    private String clientSettings;*/

   /* @Column(name = "token_settings")
    private String tokenSettings;*/

}
