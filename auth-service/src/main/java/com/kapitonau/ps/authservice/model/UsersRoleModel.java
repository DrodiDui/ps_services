package com.kapitonau.ps.authservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "users_role")
public class UsersRoleModel {
    @EmbeddedId
    private UsersRoleId userRoleId;

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "created_date", nullable = false)
    private OffsetDateTime createdDate;

}