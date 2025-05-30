package com.kapitonau.usermanagerservice.repository;

import com.kapitonau.usermanagerservice.model.UserStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStatusRepository extends JpaRepository<UserStatusModel, Long> {

    Optional<UserStatusModel> findByStatusName(String statusName);

}
