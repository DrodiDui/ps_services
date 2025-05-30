package com.kapitonau.taskservice.event.listener

import com.kapitonau.projectstudio.event.ProjectAddEventDto
import com.kapitonau.taskservice.model.TaskCounterModel
import com.kapitonau.taskservice.repository.TaskCounterRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Duration


@Component
class ProjectAddListener(
    @Value("\${kafka.topic.project-add-event.waitTimeMillis}") private final val waitTime: Long,
    private val taskCounterRepository: TaskCounterRepository,
) {

    private val log = LoggerFactory.getLogger(ProjectAddListener::class.java)

    @Transactional(transactionManager = "taskPlatformTransactionManager")
    @KafkaListener(topics = ["\${kafka.topic.project-add-event.name}"], groupId = "\${kafka.topic.project-add-event.groupId}")
    fun listen(@Payload message: ProjectAddEventDto, @Header(KafkaHeaders.OFFSET) topic: Long, ack: Acknowledgment) {
        try {
            log.info("Received message from topic: {}, data: {}", topic, message)

            val entity = TaskCounterModel()
            entity.id = message.projectId
            entity.taskCounter = 1L;
            taskCounterRepository.save(entity)

            ack.acknowledge()
            return
        } catch (e: Exception) {
            ack.nack(Duration.ofMillis(waitTime))
            return
        }
    }

}