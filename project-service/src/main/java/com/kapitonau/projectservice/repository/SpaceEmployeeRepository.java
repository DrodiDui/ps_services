package com.kapitonau.projectservice.repository;

import com.kapitonau.projectservice.model.SpaceEmployeeModel;
import com.kapitonau.projectservice.model.SpaceEmployeeModelPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceEmployeeRepository extends JpaRepository<SpaceEmployeeModel, SpaceEmployeeModelPk> {
}
