package com.kapitonau.projectservice.model.mapper;

import com.kapitonau.projectservice.model.EmployeeModel;
import com.kapitonau.projectstudio.bean.cache.ReferenceCache;
import com.kapitonau.projectstudio.event.UserAddEventDto;
import com.kapitonau.projectstudio.projectservice.dto.employee.EmployeeResponse;
import com.kapitonau.projectstudio.referenceservice.dto.ReferenceItemResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final ModelMapper modelMapper;
    private final ReferenceCache referenceCache;

    private final Converter<Long, ReferenceItemResponse> convertToReferenceItemResponse = new AbstractConverter<Long, ReferenceItemResponse>() {
        @Override
        protected ReferenceItemResponse convert(Long source) {
            if (source != null) {
                return referenceCache.getReferenceItemById(source);
            }
            return null;
        }
    };

    private final Converter<String, Long> convertRoleNameToReferenceId = new AbstractConverter<String, Long>() {
        @Override
        protected Long convert(String source) {
            if (source != null) {
                return referenceCache.getReferenceItemByTypeAndCode("EMPLOYEE_ROLE", source).referenceItemId();
            }
            return null;
        }
    };

    @PostConstruct
    public void setupMapper() {

        modelMapper
                .createTypeMap(EmployeeModel.class, EmployeeResponse.class)
                .addMappings(mapper -> {
                    mapper.map(EmployeeModel::getEmployeeId, EmployeeResponse::setEmployeeId);
                    mapper.map(EmployeeModel::getFirstName, EmployeeResponse::setFirstName);
                    mapper.map(EmployeeModel::getLastName, EmployeeResponse::setLastName);
                    mapper.map(EmployeeModel::getEmail, EmployeeResponse::setEmail);
                    mapper.using(convertToReferenceItemResponse).map(EmployeeModel::getEmployeeStatusId, EmployeeResponse::setEmployeeStatus);
                    mapper.using(convertToReferenceItemResponse).map(EmployeeModel::getEmployeeRoleId, EmployeeResponse::setEmployeeRole);
                });

        modelMapper.createTypeMap(UserAddEventDto.class, EmployeeModel.class)
                .addMappings(mapper -> {
                    mapper.map(UserAddEventDto::getFirstName, EmployeeModel::setFirstName);
                    mapper.map(UserAddEventDto::getLastName, EmployeeModel::setLastName);
                    mapper.map(UserAddEventDto::getEmail, EmployeeModel::setEmail);
                    mapper.using(convertRoleNameToReferenceId).map(UserAddEventDto::getUserRole, EmployeeModel::setEmployeeRoleId);
                });

    }

}
