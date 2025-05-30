package com.kapitonau.authserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_roles")
@Getter
@Setter
@NoArgsConstructor
public class UserRoleModel {

    @EmbeddedId
    private UserRoleId userRoleId;


    @Column(name = "space_id")
    private Long spaceId;

}
