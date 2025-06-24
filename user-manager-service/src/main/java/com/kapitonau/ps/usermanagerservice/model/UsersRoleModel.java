package com.kapitonau.ps.usermanagerservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "users_role")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersRoleModel {
    @EmbeddedId
    private UsersRoleId userRoleId;

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createdDate;

}