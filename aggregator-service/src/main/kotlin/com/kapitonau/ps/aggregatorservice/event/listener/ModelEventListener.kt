package com.kapitonau.ps.aggregatorservice.event.listener

import com.kapitonau.ps.aggregatorservice.service.processor.ModelEventDtoProcessor
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Component
class ModelEventListener(
    private @Qualifier("processorsMap") val processorsMap: Map<ModelType, ModelEventDtoProcessor>
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional(transactionManager = "aggregatorTransactionManager")
    @KafkaListener(topics = ["\${kafka-topics.model-event.name}"], groupId = "\${kafka-topics.model-event.groupId}")
    fun listen(@Payload message: ModelEventDto, @Header(KafkaHeaders.OFFSET) offset: Long, ack: Acknowledgment) {
        try {
            logger.info("Poll received message from topic: \${kafka-topics.model-event.name}: with offset: {}. Data: {}", offset, message)
            processorsMap.get(message.modelType)!!.process(message)
            ack.acknowledge()
        } catch (e: Exception) {
            logger.error("Error occurred while listening ${e.message}", e)
            ack.nack(Duration.ofMillis(60_000));
            return
        }
    }

}