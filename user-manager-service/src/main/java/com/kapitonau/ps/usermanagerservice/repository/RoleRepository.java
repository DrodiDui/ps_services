package com.kapitonau.ps.usermanagerservice.repository;

import com.kapitonau.ps.usermanagerservice.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel> findByRoleCode(String roleCode);
}
