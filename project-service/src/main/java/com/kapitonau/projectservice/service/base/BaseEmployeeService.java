package com.kapitonau.projectservice.service.base;

import com.kapitonau.commonspring.exception.CommonServiceException;
import com.kapitonau.projectservice.model.SpaceEmployeeModel;
import com.kapitonau.projectservice.model.SpaceEmployeeModelPk;
import com.kapitonau.projectservice.repository.EmployeeRepository;
import com.kapitonau.projectservice.repository.SpaceEmployeeRepository;
import com.kapitonau.projectservice.service.EmployeeService;
import com.kapitonau.projectstudio.projectservice.dto.employee.EmployeeResponse;
import com.kapitonau.projectstudio.projectservice.dto.employee.PageableEmployeeResponse;
import com.kapitonau.projectstudio.projectservice.dto.space.SpaceEmployeePostRequest;
import com.kapitonau.projectstudio.projectservice.dto.space.SpaceEmployeeRolePostRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseEmployeeService implements EmployeeService {

    private final SpaceEmployeeRepository spaceEmployeeRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageableEmployeeResponse getAllEmployees(Long offset, Long limit, Long spaceId) {
        Object count = employeeRepository.countEmployeeBySpaceId(spaceId);
        /*if (count == 0) {
            return PageableEmployeeResponse.builder()
                    .totalCount(count)
                    .employees(List.of())
                    .build();
        }*/

        List<EmployeeResponse> employees = employeeRepository.findAllBySpaceId(offset)
                .stream()
                .map(src -> modelMapper.map(src, EmployeeResponse.class))
                .toList();
        return PageableEmployeeResponse.builder()
                .totalCount(Long.valueOf(count.toString()))
                .employees(employees)
                .build();
    }

    @Override
    public EmployeeResponse getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(src -> modelMapper.map(src, EmployeeResponse.class))
                .orElseThrow(
                        () -> new CommonServiceException("PROJECT_SERVICE", "Employee not found")
                );
    }

    @Override
    public List<EmployeeResponse> getAvailableEmployees(Long spaceId) {
        return employeeRepository.findAllNotInSpace(spaceId)
                .stream()
                .map(src -> modelMapper.map(src, EmployeeResponse.class))
                .toList();
    }

    @Override
    public List<EmployeeResponse> addEmployeeToSpace(SpaceEmployeePostRequest body) {

        List<Long> employeeIds = body.getEmployees()
                .stream()
                .map(SpaceEmployeeRolePostRequest::getEmployeeId)
                .toList();

        List<SpaceEmployeeModel> spaceEmployee = body.getEmployees().stream()
                .map(it -> {
                    return SpaceEmployeeModel.builder()
                            .spaceEmployeeId(new SpaceEmployeeModelPk(body.getSpaceId(), it.getEmployeeId()))
                            .employeeSpaceRole(it.getSpaceEmployeeRoleId())
                            .build();
                })
                .toList();

        spaceEmployeeRepository.saveAll(spaceEmployee);

        return employeeRepository.findAllById(employeeIds)
                .stream()
                .map(it -> modelMapper.map(it, EmployeeResponse.class))
                .toList();
    }
}
