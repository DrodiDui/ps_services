package com.kapitonau.projectservice.model;

import com.kapitonau.projectservice.model.audit.BaseAuditableModel;
import com.kapitonau.projectservice.model.audit.BaseAuditableModelListener;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(BaseAuditableModelListener.class)
public class EmployeeModel extends BaseAuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_id_gen")
    @SequenceGenerator(name = "employees_id_gen", sequenceName = "employees_employee_id_seq", allocationSize = 1)
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "first_name", nullable = false, length = Integer.MAX_VALUE)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = Integer.MAX_VALUE)
    private String lastName;

    @Column(name = "middle_name", length = Integer.MAX_VALUE)
    private String middleName;

    @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "employee_role_id", nullable = false)
    private Long employeeRoleId;

    @Column(name = "employee_status_id", nullable = false)
    private Long employeeStatusId;

/*    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<SpaceEmployeeModel> spaceEmployees = new HashSet<>();*/

}