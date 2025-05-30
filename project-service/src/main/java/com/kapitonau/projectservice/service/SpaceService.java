package com.kapitonau.projectservice.service;

import com.kapitonau.projectstudio.projectservice.dto.space.SpaceEmployeePostRequest;
import com.kapitonau.projectstudio.projectservice.dto.space.SpacePostRequest;
import com.kapitonau.projectstudio.projectservice.dto.space.SpaceResponse;

import java.util.List;

public interface SpaceService {
    List<SpaceResponse> getAllSpaces();

    SpaceResponse createSpace(SpacePostRequest body);

    SpaceResponse checkSpaceExistingOrGet(Long spaceId);

}
