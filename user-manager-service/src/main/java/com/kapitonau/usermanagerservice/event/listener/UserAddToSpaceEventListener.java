package com.kapitonau.usermanagerservice.event.listener;

import com.kapitonau.projectstudio.event.AddUserToSpaceEventDto;
import com.kapitonau.usermanagerservice.model.UserRoleId;
import com.kapitonau.usermanagerservice.model.UsersRoleModel;
import com.kapitonau.usermanagerservice.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAddToSpaceEventListener {

    private final UserRoleRepository userRoleRepository;

    @Value("${kafka.topic.user-add-to-space-event.sleepTimeMs}")
    private Long slipTimeMs;

    @Transactional(transactionManager = "userManagerTransaction")
    @KafkaListener(topics = "${kafka.topic.user-add-to-space-event.name}", groupId = "${kafka.topic.user-add-to-space-event.groupId}")
    public void listen(@Payload AddUserToSpaceEventDto message, @Header(KafkaHeaders.OFFSET) Long offset, Acknowledgment ack) {
        log.info("Received AddUserToSpaceEventDto: offset {}, message {}", offset, message);
        try {
            userRoleRepository.save(new UsersRoleModel(
                    new UserRoleId(message.getUserId(), message.getSpaceId()),
                    message.getSpaceRoleId()
            ));
            ack.acknowledge();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ack.nack(Duration.ofMillis(slipTimeMs));
        }
    }

}
