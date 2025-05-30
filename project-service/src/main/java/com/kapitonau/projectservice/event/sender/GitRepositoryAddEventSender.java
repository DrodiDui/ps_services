package com.kapitonau.projectservice.event.sender;

import com.kapitonau.projectstudio.event.GtiRepositoryAddEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GitRepositoryAddEventSender {

    private final KafkaTemplate<String, GtiRepositoryAddEventDto> kafkaTemplate;

    @Value("${kafka.topic.git-repository-add-event.name}")
    private String topic;

    public void send(GtiRepositoryAddEventDto message) {
        log.info("Sending message to {}. Message: {}", topic, message);
        kafkaTemplate.send(topic, message);
    }

}
