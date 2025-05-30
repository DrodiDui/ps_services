package com.kapitonau.usermanagerservice.event.sender;

import com.kapitonau.projectstudio.event.UserAddEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAddEventSender {

    private final KafkaTemplate<String, UserAddEventDto> kafkaTemplate;

    @Value("${kafka.topic.user-add-event.name}")
    private String topic;

    public void send(UserAddEventDto userAddEventDto) {
        log.info("Send user add event: {}", userAddEventDto);
        kafkaTemplate.send(topic, userAddEventDto);
    }

}
