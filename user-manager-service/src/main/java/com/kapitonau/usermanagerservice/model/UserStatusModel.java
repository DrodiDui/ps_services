package com.kapitonau.usermanagerservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_statuses")
public class UserStatusModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id", nullable = false)
    private Long id;

    @Column(name = "status_name", nullable = false, length = 25)
    private String statusName;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

}