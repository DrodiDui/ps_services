package com.kapitonau.ps.authservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class AuthorityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorities_id_gen")
    @SequenceGenerator(name = "authorities_id_gen", sequenceName = "authorities_authority_id_seq", allocationSize = 1)
    @Column(name = "authority_id", nullable = false)
    private Long authorityId;

    @Column(name = "authority_code", nullable = false, length = Integer.MAX_VALUE)
    private String authorityCode;

    @Column(name = "authority_name", nullable = false, length = Integer.MAX_VALUE)
    private String authorityName;

    @Column(name = "description", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> description;

}