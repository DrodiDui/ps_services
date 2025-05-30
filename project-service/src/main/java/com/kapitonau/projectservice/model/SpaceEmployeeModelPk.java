package com.kapitonau.projectservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SpaceEmployeeModelPk implements Serializable {
    private static final long serialVersionUID = -8900277722633214157L;


    @Column(name = "space_id", nullable = false)
    private Long spaceId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

}