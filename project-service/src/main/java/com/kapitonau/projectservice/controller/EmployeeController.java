package com.kapitonau.projectservice.controller;

import com.kapitonau.projectservice.service.EmployeeService;
import com.kapitonau.projectstudio.projectservice.api.EmployeeApi;
import com.kapitonau.projectstudio.projectservice.dto.employee.EmployeeResponse;
import com.kapitonau.projectstudio.projectservice.dto.employee.PageableEmployeeResponse;
import com.kapitonau.projectstudio.projectservice.dto.space.SpaceEmployeePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;

    @Override
    public PageableEmployeeResponse getAllEmployees(Long offset, Long limit, Long spaceId) {
        return employeeService.getAllEmployees(offset, limit, spaceId);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @Override
    public List<EmployeeResponse> getAvailableEmployees(Long spaceId) {
        return employeeService.getAvailableEmployees(spaceId);
    }

    @Override
    public List<EmployeeResponse> addEmployeeToSpace(SpaceEmployeePostRequest body) {
        return employeeService.addEmployeeToSpace(body);
    }
}
