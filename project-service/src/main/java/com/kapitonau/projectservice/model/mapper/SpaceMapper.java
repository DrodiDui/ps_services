package com.kapitonau.projectservice.model.mapper;

import com.kapitonau.projectstudio.projectservice.dto.space.SpacePostRequest;
import com.kapitonau.projectstudio.projectservice.dto.space.SpaceResponse;
import com.kapitonau.projectservice.model.SpaceModel;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpaceMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void setupMapper() {
        modelMapper
                .createTypeMap(SpaceModel.class, SpaceResponse.class)
                .addMappings(mapper -> {
                    mapper.map(SpaceModel::getSpaceId, SpaceResponse::setSpaceId);
                    mapper.map(SpaceModel::getTitle, SpaceResponse::setTitle);
                    mapper.map(SpaceModel::getDescription, SpaceResponse::setDescription);
                });

        modelMapper
                .createTypeMap(SpacePostRequest.class, SpaceModel.class)
                .addMappings(mapper -> {
                    mapper.map(SpacePostRequest::getTitle, SpaceModel::setTitle);
                    mapper.map(SpacePostRequest::getDescription, SpaceModel::setDescription);
                });
    }
}
