package com.kapitonau.projectservice.service;

import com.kapitonau.projectstudio.projectservice.dto.employee.EmployeeResponse;
import com.kapitonau.projectstudio.projectservice.dto.employee.PageableEmployeeResponse;
import com.kapitonau.projectstudio.projectservice.dto.space.SpaceEmployeePostRequest;

import java.util.List;

public interface EmployeeService {
    PageableEmployeeResponse getAllEmployees(Long offset, Long limit, Long spaceId);

    EmployeeResponse getEmployeeById(Long employeeId);

    List<EmployeeResponse> getAvailableEmployees(Long spaceId);

    List<EmployeeResponse> addEmployeeToSpace(SpaceEmployeePostRequest body);
}
