package com.kapitonau.projectservice.model;

import com.kapitonau.projectservice.model.audit.BaseAuditableModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(BaseAuditableModel.class)
public class ProjectModel extends BaseAuditableModel {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "projects_project_id_seq")
    @SequenceGenerator(name = "projects_project_id_seq", sequenceName = "projects_project_id_seq", allocationSize = 1)
    private Long projectId;

    @Column(name = "space_id")
    private Long spaceId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "project_status_id")
    private Long projectStatusId;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "expected_close_date")
    private LocalDate expectedCloseDate;

}
