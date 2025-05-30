package com.kapitonau.projectservice.service.base;

import com.kapitonau.commonspring.exception.CommonServiceException;
import com.kapitonau.projectstudio.projectservice.dto.space.SpacePostRequest;
import com.kapitonau.projectstudio.projectservice.dto.space.SpaceResponse;
import com.kapitonau.projectservice.model.SpaceModel;
import com.kapitonau.projectservice.repository.SpaceRepository;
import com.kapitonau.projectservice.service.SpaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseSpaceService implements SpaceService {

    private final ModelMapper modelMapper;
    private final SpaceRepository spaceRepository;

    @Override
    public List<SpaceResponse> getAllSpaces() {
        return spaceRepository.findAll()
                .stream()
                .map(src -> modelMapper.map(src, SpaceResponse.class))
                .toList();
    }

    @Override
    public SpaceResponse createSpace(SpacePostRequest body) {
        if (spaceRepository.findByTitle(body.getTitle()).isPresent()) {
            throw new CommonServiceException("PROJECT_SERVICE", "Space with title already exists");
        }
        SpaceModel spaceModel = modelMapper.map(body, SpaceModel.class);
        spaceModel = spaceRepository.save(spaceModel);

        return modelMapper.map(spaceModel, SpaceResponse.class);
    }

    @Override
    public SpaceResponse checkSpaceExistingOrGet(Long spaceId) {
        return spaceRepository.findById(spaceId)
                .map(src -> modelMapper.map(src, SpaceResponse.class))
                .orElseThrow(() -> new CommonServiceException("PROJECT_SERVICE", "Space with id not found"));
    }
}
