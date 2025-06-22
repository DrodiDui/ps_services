package com.kapitonau.ps.authservice.repository;

import com.kapitonau.ps.authservice.model.AuthorityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface AuthorityRepository extends JpaRepository<AuthorityModel, Long> {

    @Query("""
            select a from AuthorityModel a left join RolesAuthorityModel ra on ra.rolesAuthorityId.authorityId = a.authorityId 
            where ra.rolesAuthorityId.roleId in (:roleIds)
            """)
    List<AuthorityModel> findAllByRoleIds(Set<Long> roleIds);
}
