package com.kapitonau.ps.authservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles_authority")
public class RolesAuthorityModel {
    @EmbeddedId
    private RolesAuthorityId rolesAuthorityId;

}