package com.kapitonau.projectservice.controller;

import com.kapitonau.projectservice.service.SpaceService;
import com.kapitonau.projectstudio.projectservice.api.SpaceApi;
import com.kapitonau.projectstudio.projectservice.dto.employee.EmployeeResponse;
import com.kapitonau.projectstudio.projectservice.dto.space.SpaceEmployeePostRequest;
import com.kapitonau.projectstudio.projectservice.dto.space.SpacePostRequest;
import com.kapitonau.projectstudio.projectservice.dto.space.SpaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpaceController implements SpaceApi {

    private final SpaceService spaceService;

    @Override
    public List<SpaceResponse> getAllSpaces() {
        return spaceService.getAllSpaces();
    }

    @Override
    public SpaceResponse createSpace(SpacePostRequest body) {
        return spaceService.createSpace(body);
    }
}
