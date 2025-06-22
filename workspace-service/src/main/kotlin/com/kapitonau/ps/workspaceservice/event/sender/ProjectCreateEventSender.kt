package com.kapitonau.ps.workspaceservice.event.sender

import com.kapitonau.ps.apirequestlib.kafka.ProjectCreateEventMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ProjectCreateEventSender (
    private val kafkaTemplate: KafkaTemplate<String, ProjectCreateEventMessage>,
    @Value("\${kafka-topics.project-create-event.name}") private val topic: String
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun send(message: ProjectCreateEventMessage) {
        logger.info("Send model to task service. Data: {}", message)
        kafkaTemplate.send(topic, message)
    }

}