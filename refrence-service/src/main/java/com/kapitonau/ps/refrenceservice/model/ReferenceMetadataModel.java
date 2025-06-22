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
@Table(name = "reference_metadata")
public class ReferenceMetadataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metadata_id", nullable = false)
    private Long id;

    @Column(name = "reference_item_id")
    private Long referenceItemId;

    @Column(name = "workspace_id")
    private Long workspaceId;

    @Column(name = "metadata_name")
    private String metadataName;

    @Column(name = "metadata", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metadata;

}