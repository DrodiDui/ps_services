package com.kapitonau.ps.taskservice.event.sender

import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ModelEventSender(
    private val kafkaTemplate: KafkaTemplate<String, ModelEventDto>,
    @Value("\${kafka-topics.model-event.name}") private val topic: String
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun send(modelEvent: ModelEventDto) {
        logger.info("Send model to aggregator service. Data: {}", modelEvent)
        kafkaTemplate.send(topic, modelEvent)
    }

}