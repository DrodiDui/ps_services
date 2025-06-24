package com.kapitonau.ps.usermanagerservice.repository;

import com.kapitonau.ps.usermanagerservice.model.UsersRoleId;
import com.kapitonau.ps.usermanagerservice.model.UsersRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UsersRoleModel, UsersRoleId> {
}
