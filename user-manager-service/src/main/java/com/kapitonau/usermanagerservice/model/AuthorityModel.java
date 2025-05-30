package com.kapitonau.usermanagerservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class AuthorityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id", nullable = false)
    private Long id;

    @Column(name = "authority_code", nullable = false, length = 75)
    private String authorityCode;

    @Column(name = "description", nullable = false, length = 75)
    private String description;

}