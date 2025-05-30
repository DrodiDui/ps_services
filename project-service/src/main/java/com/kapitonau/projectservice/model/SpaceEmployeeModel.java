package com.kapitonau.projectservice.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "space_employees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpaceEmployeeModel {
    @EmbeddedId
    private SpaceEmployeeModelPk spaceEmployeeId;

/*
    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private EmployeeModel employee;

    @ManyToOne
    @MapsId("spaceId")
    @JoinColumn(name = "space_id", referencedColumnName = "space_id")
    private SpaceModel space;
*/

    @Column(name = "employee_space_role", nullable = false)
    private Long employeeSpaceRole;

}