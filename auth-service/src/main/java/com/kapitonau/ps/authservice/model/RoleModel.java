package com.kapitonau.ps.authservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_gen")
    @SequenceGenerator(name = "roles_id_gen", sequenceName = "roles_role_id_seq", allocationSize = 1)
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "role_code", nullable = false, length = Integer.MAX_VALUE)
    private String roleCode;

    @Column(name = "role_name", nullable = false, length = Integer.MAX_VALUE)
    private String roleName;

    @Column(name = "description", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> description;

    @Column(name = "created_date", nullable = false)
    private OffsetDateTime createdDate;

    @Column(name = "last_modifying_date")
    private OffsetDateTime lastModifyingDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "last_modifying_by")
    private Long lastModifyingBy;

}