package com.kapitonau.ps.authservice.repository;

import com.kapitonau.ps.authservice.model.UsersRoleId;
import com.kapitonau.ps.authservice.model.UsersRoleModel;
import com.kapitonau.ps.authservice.repository.projection.UserWorkspaceAuthoritiesProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UsersRoleModel, UsersRoleId> {

    List<UsersRoleModel> findAllByUserRoleId_UserId(Long userId);

    @Query(value = """
            select
                        r.role_name as role_name, 
                        ur.workspace_id as workspace_id, 
                        a.authority_name as authority_name
            from users u
                     join users_role ur on ur.user_id = u.user_id
                     join roles r on r.role_id = ur.role_id
                     join roles_authority ra on ra.role_id = r.role_id
                     join authorities a on a.authority_id = ra.authority_id
            where u.user_id = :userId
              and u.is_active = true
            """, nativeQuery = true)
    List<UserWorkspaceAuthoritiesProjection> findUserWorkspaceAuthoritiesByUserId(Long userId);
}
