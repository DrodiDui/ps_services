package com.kapitonau.ps.authservice.repository;

import com.kapitonau.ps.authservice.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientModel, Long> {

    Optional<ClientModel> findByClientId(String clientId);

}
