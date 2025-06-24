package com.kapitonau.ps.usermanagerservice.event.listener;

import com.kapitonau.ps.apirequestlib.kafka.UserWorkspaceEventMessage;
import com.kapitonau.ps.usermanagerservice.model.UsersRoleId;
import com.kapitonau.ps.usermanagerservice.model.UsersRoleModel;
import com.kapitonau.ps.usermanagerservice.repository.UserRoleRepository;
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
public class WorkspaceUserRoleListener {

    private final UserRoleRepository userRoleRepository;

    @Value("${kafka-topics.user-workspace-role-event.name}")
    private String topic;

    @Transactional(transactionManager = "userTransactionManager")
    @KafkaListener(topics = "${kafka-topics.user-workspace-role-event.name}", groupId = "${kafka-topics.user-workspace-role-event.groupId}")
    public void listen(@Payload UserWorkspaceEventMessage message, @Header(KafkaHeaders.OFFSET) Long offset, Acknowledgment ack) {
        try {
            log.info("Poll topic {} with offset{}. Message: {}", topic, offset, message);

            userRoleRepository.save(new UsersRoleModel(
                    new UsersRoleId(message.getUserId(), message.getRoleId()),
                    message.getWorkspaceId(),
                    message.getCreatedTime()
            ));

            ack.acknowledge();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ack.nack(Duration.ofMillis(60_000));
        }
    }

}
