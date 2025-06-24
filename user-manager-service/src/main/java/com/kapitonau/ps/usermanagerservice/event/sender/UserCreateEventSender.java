package com.kapitonau.ps.usermanagerservice.event.sender;

import com.kapitonau.ps.apirequestlib.kafka.MemberCreateEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreateEventSender {

    private final KafkaTemplate<String, MemberCreateEventMessage> kafkaTemplate;

    @Value("${kafka-topics.member-create-event.name}")
    private String topic;

    public void send(MemberCreateEventMessage message) {
        kafkaTemplate.send(topic, message);
    }

}
