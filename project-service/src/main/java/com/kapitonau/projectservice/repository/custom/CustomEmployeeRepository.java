package com.kapitonau.projectservice.repository.custom;

import com.kapitonau.projectservice.model.EmployeeModel;

import java.util.List;

public interface CustomEmployeeRepository {

    List<EmployeeModel> findAllByFilters(Long offset, Long limit, Long spaceId);

    Long countEmployees(Long spaceId);

}
