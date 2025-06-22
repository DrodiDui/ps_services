package com.kapitonau.ps.authservice.repository.custom;

import com.kapitonau.ps.authservice.mapper.ClientMapper;
import com.kapitonau.ps.authservice.model.ClientModel;
import com.kapitonau.ps.authservice.repository.ClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CustomClientRepository implements RegisteredClientRepository {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    public CustomClientRepository(
            ClientMapper clientMapper,
            ClientRepository clientRepository
    ) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        ClientModel clientModel = clientMapper.toClientModel(registeredClient);
        clientRepository.save(clientModel);
    }

    @Override
    public RegisteredClient findById(String id) {
        return clientRepository.findById(Long.valueOf(id))
                .map(it -> clientMapper.toRegisteredClient(it))
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId)
                .map(it -> clientMapper.toRegisteredClient(it))
                .orElseThrow(() -> new RuntimeException());
    }
}
