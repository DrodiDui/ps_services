package com.kapitonau.projectservice.event.sender;
import com.kapitonau.projectstudio.event.ProjectAddEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectAddEventSender {

    private final KafkaTemplate<String, ProjectAddEventDto> kafkaTemplate;

    @Value("${kafka.topic.project-add-event.name}")
    private String topic;

    public void send(ProjectAddEventDto projectAddEventDto) {
        log.info("Sending project add event {}", projectAddEventDto);
        kafkaTemplate.send(topic, projectAddEventDto);
    }


}
