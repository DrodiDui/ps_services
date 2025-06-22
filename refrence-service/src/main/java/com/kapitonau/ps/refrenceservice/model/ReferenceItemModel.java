package com.kapitonau.ps.refrenceservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "reference_items")
public class ReferenceItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reference_item_id", nullable = false)
    private Long referenceItemId;

    @Column(name = "reference_type", nullable = false, length = Integer.MAX_VALUE)
    private String referenceType;

    @Column(name = "item_code", nullable = false, length = Integer.MAX_VALUE)
    private String itemCode;

    @Column(name = "description", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

}