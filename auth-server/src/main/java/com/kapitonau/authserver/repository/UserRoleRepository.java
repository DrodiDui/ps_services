package com.kapitonau.authserver.repository;

import com.kapitonau.authserver.model.UserRoleId;
import com.kapitonau.authserver.model.UserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleModel, UserRoleId> {

    List<UserRoleModel> findAllByUserRoleIdIn(List<UserRoleId> userRoleId);

}
