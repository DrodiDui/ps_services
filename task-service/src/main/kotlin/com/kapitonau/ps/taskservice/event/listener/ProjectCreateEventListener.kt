package com.kapitonau.ps.taskservice.event.listener

import com.kapitonau.ps.apirequestlib.kafka.ProjectCreateEventMessage
import com.kapitonau.ps.taskservice.model.ProjectTaskCounterModel
import com.kapitonau.ps.taskservice.repository.ProjectTaskCounterRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class ProjectCreateEventListener(
    private @Value("\${kafka-topics.project-create-event.name}") val topicName: String,
    private val projectTaskCounterRepository: ProjectTaskCounterRepository,
) {

    private val logger = LoggerFactory.getLogger(ProjectCreateEventListener::class.java)

    @KafkaListener(topics = ["\${kafka-topics.project-create-event.name}"], groupId = "\${kafka-topics.project-create-event.groupId}")
    fun listen(@Payload message: ProjectCreateEventMessage, @Header(KafkaHeaders.OFFSET) offset: Long, ack: Acknowledgment) {
        try {
            logger.info("Received event from {}, offset: {}. Data: {}", topicName, offset, message)

            val model = ProjectTaskCounterModel()
            model.projectId = message.projectId
            model.counter = 1L
            projectTaskCounterRepository.save(model)

            ack.acknowledge()
        } catch (e: Exception) {
            logger.error("Error creating event message {}", e.message)
            ack.nack(Duration.ofMillis(60 * 1000))
        }
    }

}