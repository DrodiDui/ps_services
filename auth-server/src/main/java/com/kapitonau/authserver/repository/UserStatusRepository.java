package com.kapitonau.authserver.repository;

import com.kapitonau.authserver.model.UserStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatusModel, Long> {
}
