package com.kapitonau.ps.usermanagerservice.repository;

import com.kapitonau.ps.usermanagerservice.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
