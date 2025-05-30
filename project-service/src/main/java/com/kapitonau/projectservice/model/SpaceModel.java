package com.kapitonau.projectservice.model;

import com.kapitonau.projectservice.model.audit.BaseAuditableModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "spaces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpaceModel extends BaseAuditableModel {

    @Id
    @Column(name = "space_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spaces_space_id_seq")
    @SequenceGenerator(name = "spaces_space_id_seq", sequenceName = "spaces_space_id_seq", allocationSize = 1)
    private Long spaceId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

}
