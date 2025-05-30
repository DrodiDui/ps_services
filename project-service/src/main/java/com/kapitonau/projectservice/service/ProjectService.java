package com.kapitonau.projectservice.service;

import com.kapitonau.projectstudio.projectservice.dto.project.ProjectPostRequest;
import com.kapitonau.projectstudio.projectservice.dto.project.ProjectResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> getProjects();

    ProjectResponse getProject(Long projectId);

    ProjectResponse createProject(ProjectPostRequest body);
}
