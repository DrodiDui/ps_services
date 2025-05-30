package com.kapitonau.authserver.oauth.service;

import com.kapitonau.authserver.cache.UserStatusCache;
import com.kapitonau.authserver.exception.OAuth2AuthorizationServerException;
import com.kapitonau.authserver.model.UserModel;
import com.kapitonau.authserver.oauth.mapper.UserMapper;
import com.kapitonau.authserver.oauth.model.CustomUserDetails;
import com.kapitonau.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;
    private final MessageSource messageSource;
    private final UserRepository userRepository;
    private final UserStatusCache userStatusCache;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new OAuth2AuthorizationServerException(
                        "AUTH_SERVER_ERROR_1",
                        messageSource.getMessage("AUTH_SERVER_ERROR_1", null, getLocale())
                ));

        if (!"ACTIVE".equals(userStatusCache.getUserStatus(user.getUserStatusId()).getStatusName())) {
            throw new OAuth2AuthorizationServerException(
                    "AUTH_SERVER_ERROR_3",
                    messageSource.getMessage("AUTH_SERVER_ERROR_3", null, getLocale()
                    ));
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new OAuth2AuthorizationServerException(
                    "AUTH_SERVER_ERROR_4",
                    messageSource.getMessage("AUTH_SERVER_ERROR_4", null, getLocale()
                    ));
        }

        return userMapper.mapToUserDetails(user);
    }
}
