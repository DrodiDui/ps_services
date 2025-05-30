package com.kapitonau.authserver.cache;

import com.kapitonau.authserver.exception.OAuth2AuthorizationServerException;
import com.kapitonau.authserver.model.UserStatusModel;
import com.kapitonau.authserver.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStatusCache {

    private static final Map<Long, UserStatusModel> USER_STATUS_CACHE = new ConcurrentHashMap<>();

    private final MessageSource messageSource;
    private final UserStatusRepository userStatusRepository;


    public UserStatusModel getUserStatus(Long userId) {
        if (USER_STATUS_CACHE.containsKey(userId)) {
            return USER_STATUS_CACHE.get(userId);
        } else {
            UserStatusModel userStatus = userStatusRepository.findById(userId)
                    .orElseThrow(() -> {
                        log.error("It's not possible to save user status to cache by id. User status not found");
                        return new OAuth2AuthorizationServerException(
                                "AUTH_SERVER_ERROR_2",
                                messageSource.getMessage("AUTH_SERVER_ERROR_2", null, getLocale())
                        );
                    });
            USER_STATUS_CACHE.put(userId, userStatus);
            return userStatus;
        }
    }

}
