package com.kapitonau.ps.authservice.repository;

import com.kapitonau.ps.authservice.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query("select u from UserModel u where u.username = :username or u.email = :username")
    Optional<UserModel> findByUsernameOrEmail(String username);

    Optional<UserModel> findByUsername(String username);
}
