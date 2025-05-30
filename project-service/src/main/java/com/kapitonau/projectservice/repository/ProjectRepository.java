package com.kapitonau.projectservice.repository;

import com.kapitonau.projectservice.model.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectModel, Long> {

    Optional<ProjectModel> findByTitleAndSpaceId(String title, Long spaceId);

}
