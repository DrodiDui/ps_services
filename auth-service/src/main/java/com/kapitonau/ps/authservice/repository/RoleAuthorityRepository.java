package com.kapitonau.ps.authservice.repository;

import com.kapitonau.ps.authservice.model.RolesAuthorityId;
import com.kapitonau.ps.authservice.model.RolesAuthorityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleAuthorityRepository extends JpaRepository<RolesAuthorityModel, RolesAuthorityId> {
}
