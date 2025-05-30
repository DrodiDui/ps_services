package com.kapitonau.projectservice.controller;

import com.kapitonau.projectstudio.projectservice.api.ProjectApi;
import com.kapitonau.projectstudio.projectservice.dto.project.ProjectPostRequest;
import com.kapitonau.projectstudio.projectservice.dto.project.ProjectResponse;
import com.kapitonau.projectservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController implements ProjectApi {

    private final ProjectService projectService;

    @Override
    public List<ProjectResponse> getProjects() {
        return projectService.getProjects();
    }

    @Override
    public ProjectResponse getProject(Long projectId) {
        return projectService.getProject(projectId);
    }

    @Override
    public ProjectResponse createProject(ProjectPostRequest body) {
        return projectService.createProject(body);
    }
}
