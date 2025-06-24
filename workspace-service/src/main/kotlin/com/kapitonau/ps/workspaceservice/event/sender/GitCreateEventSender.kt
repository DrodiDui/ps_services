package com.kapitonau.ps.workspaceservice.event.sender

import com.kapitonau.ps.apirequestlib.kafka.GitCreateEventMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class GitCreateEventSender(
    private val kafkaTemplate: KafkaTemplate<String, GitCreateEventMessage>,
    @Value("\${kafka-topics.git-create-event.name}") private val topic: String
) {

    private final val logger = LoggerFactory.getLogger(javaClass)

    fun send(message: GitCreateEventMessage) {
        kafkaTemplate.send(topic, message)
        logger.info("Send message to topic $topic. Data: $message")
    }

}