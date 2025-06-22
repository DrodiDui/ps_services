package com.kapitonau.ps.authservice.repository;

import com.kapitonau.ps.authservice.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {

    @Query("""
            select r from RoleModel r left join UsersRoleModel ur on ur.userRoleId.roleId = r.roleId
            where ur.userRoleId.userId = :userId
            """)
    List<RoleModel> findAllByUserId(Long userId);
}
