package com.kapitonau.projectservice.model.mapper;

import com.kapitonau.projectservice.model.ProjectModel;
import com.kapitonau.projectstudio.bean.cache.ReferenceCache;
import com.kapitonau.projectstudio.projectservice.dto.project.ProjectPostRequest;
import com.kapitonau.projectstudio.projectservice.dto.project.ProjectResponse;
import com.kapitonau.projectstudio.referenceservice.dto.ReferenceItemResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final ReferenceCache referenceCache;

    private final ModelMapper modelMapper;

    private final Converter<Long, ReferenceItemResponse> referenceTypeIdToResponse = new AbstractConverter<Long, ReferenceItemResponse>() {
        @Override
        protected ReferenceItemResponse convert(Long source) {
            if (source != null) {
                return referenceCache.getReferenceItemById(source);
            }
            return null;
        }
    };

    @PostConstruct
    public void setModelMapper() {
        modelMapper
                .createTypeMap(ProjectModel.class, ProjectResponse.class)
                .addMappings(mapper -> {
                    mapper.map(ProjectModel::getProjectId, ProjectResponse::setProjectId);
                    mapper.map(ProjectModel::getSpaceId, ProjectResponse::setSpaceId);
                    mapper.map(ProjectModel::getTitle, ProjectResponse::setTitle);
                    mapper.map(ProjectModel::getDescription, ProjectResponse::setDescription);
                    mapper.using(referenceTypeIdToResponse).map(ProjectModel::getProjectStatusId, ProjectResponse::setProjectStatus);
                    mapper.map(ProjectModel::getCreatedDate, ProjectResponse::setCreatedDate);
                    mapper.map(ProjectModel::getExpectedCloseDate, ProjectResponse::setExpectedCloseDate);
                });

        modelMapper
                .createTypeMap(ProjectPostRequest.class, ProjectModel.class)
                .addMappings(mapper -> {
                    mapper.map(ProjectPostRequest::getSpaceId, ProjectModel::setSpaceId);
                    mapper.map(ProjectPostRequest::getTitle, ProjectModel::setTitle);
                    mapper.map(ProjectPostRequest::getDescription, ProjectModel::setDescription);
                    mapper.map(ProjectPostRequest::getExpectedCloseDate, ProjectModel::setExpectedCloseDate);
                });
    }

}
