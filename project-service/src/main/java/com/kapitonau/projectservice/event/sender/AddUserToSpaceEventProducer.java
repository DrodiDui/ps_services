package com.kapitonau.projectservice.event.sender;

import com.kapitonau.projectstudio.event.AddUserToSpaceEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddUserToSpaceEventProducer {

    private final KafkaTemplate<String, AddUserToSpaceEventDto> kafkaTemplate;

    @Value("${kafka.topic.user-add-to-space-event.name}")
    private String topic;

    public void send(AddUserToSpaceEventDto addUserToSpaceEventDto) {
        log.info("Sending message to topic: {}. Data: {}", topic, addUserToSpaceEventDto);
        kafkaTemplate.send(topic, addUserToSpaceEventDto);
    }

}
