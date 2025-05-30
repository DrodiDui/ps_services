package com.kapitonau.authserver.oauth.repository.custom;

import com.kapitonau.authserver.exception.OAuth2AuthorizationServerException;
import com.kapitonau.authserver.oauth.mapper.ClientMapper;
import com.kapitonau.authserver.oauth.model.RegisteredClientModel;
import com.kapitonau.authserver.oauth.repository.ClientRepository;
import org.springframework.context.MessageSource;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Repository
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final MessageSource messageSource;
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    public CustomRegisteredClientRepository(
            MessageSource messageSource,
            ClientMapper clientMapper,
            ClientRepository clientRepository
    ) {
        this.messageSource = messageSource;
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        RegisteredClientModel client = clientMapper.toClient(registeredClient);
        client =  clientRepository.save(client);
        System.out.println(client);
    }

    @Override
    public RegisteredClient findById(String id) {
        return clientRepository.findById(Long.valueOf(id))
                .map(clientMapper::toResponse)
                .orElseThrow(() -> new OAuth2AuthorizationServerException(
                        "",
                        messageSource.getMessage("", null, getLocale())
                ));
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId)
                .map(clientMapper::toResponse)
                .orElseThrow(() -> new OAuth2AuthorizationServerException(
                        "",
                        messageSource.getMessage("", null, getLocale())
                ));
    }
}
