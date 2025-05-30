package com.kapitonau.projectservice.repository;

import com.kapitonau.projectservice.model.SpaceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpaceRepository extends JpaRepository<SpaceModel, Long> {

    Optional<SpaceModel> findByTitle(String title);

}
