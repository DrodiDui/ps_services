package com.kapitonau.ps.usermanagerservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "username", nullable = false, length = Integer.MAX_VALUE)
    private String username;

    @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
    private String password;

    @Column(name = "first_name", nullable = false, length = Integer.MAX_VALUE)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = Integer.MAX_VALUE)
    private String lastName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "activation_code", length = Integer.MAX_VALUE)
    private String activationCode;

    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private ZonedDateTime createdDate;

    @Column(name = "last_modifying_date")
    @LastModifiedDate
    private ZonedDateTime lastModifyingDate;

    @Column(name = "created_by")
    @CreatedBy
    private Long createdBy;

    @Column(name = "last_modifying_by")
    @LastModifiedBy
    private Long lastModifyingBy;

}