package com.kapitonau.authserver.oauth.repository;

import com.kapitonau.authserver.oauth.model.RegisteredClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<RegisteredClientModel, Long> {

    Optional<RegisteredClientModel> findByClientId(String clientId);

}
