package com.kapitonau.usermanagerservice.repository;


import com.kapitonau.usermanagerservice.model.UserRoleId;
import com.kapitonau.usermanagerservice.model.UsersRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UsersRoleModel, UserRoleId> {
}
