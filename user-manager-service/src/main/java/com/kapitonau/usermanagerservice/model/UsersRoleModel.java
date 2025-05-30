package com.kapitonau.usermanagerservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users_roles")
@NoArgsConstructor
@AllArgsConstructor
public class UsersRoleModel {

    @EmbeddedId
    private UserRoleId userRoleId;

   /* @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleModel role;*/

    @Column(name = "space_id", nullable = false)
    private Long spaceId;

}