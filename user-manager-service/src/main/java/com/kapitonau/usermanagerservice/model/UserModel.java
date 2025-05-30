package com.kapitonau.usermanagerservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "users_user_id_seq")
    @SequenceGenerator(name = "users_user_id_seq", sequenceName = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 75)
    private String email;

    @Column(name = "username", nullable = false, length = 75)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "family_name", nullable = false, length = 50)
    private String familyName;

    @Column(name = "user_status_id")
    private Long userStatusId;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @ColumnDefault("0")
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

    @Column(name = "action_type", length = 25)
    private String actionType;

}