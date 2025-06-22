package com.kapitonau.ps.refrenceservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "reference_types")
public class ReferenceTypeModel {
    @Id
    @Column(name = "reference_type", nullable = false, length = Integer.MAX_VALUE)
    private String referenceType;

    @Column(name = "description", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> description;

}