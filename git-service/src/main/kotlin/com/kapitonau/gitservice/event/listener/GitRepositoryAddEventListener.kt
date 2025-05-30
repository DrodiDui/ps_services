package com.kapitonau.gitservice.event.listener

import com.kapitonau.gitservice.model.GitRepositoryModel
import com.kapitonau.gitservice.repository.GitRepositoryRepository
import com.kapitonau.projectstudio.bean.cache.ReferenceCache
import com.kapitonau.projectstudio.event.GtiRepositoryAddEventDto
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
class GitRepositoryAddEventListener(
    private val gitRepository: GitRepositoryRepository,
    private val referenceCache: ReferenceCache,
    @Value("\${kafka.topic.git-repository-add-event.sleepTimeMs}") private val sleepTimeMs: Long
) {

    private val log = LoggerFactory.getLogger(GitRepositoryAddEventListener::class.java)

    @Transactional(transactionManager = "gitTransaction")
    @KafkaListener(topics = ["\${kafka.topic.git-repository-add-event.name}"], groupId = "\${kafka.topic.git-repository-add-event.groupId}")
    fun listen(
        @Payload message: GtiRepositoryAddEventDto,
        @Header(KafkaHeaders.OFFSET) offset: Long,
        ack: Acknowledgment
    ) {
        try {
            log.info("Poll message from \${kafka.topic.git-repository-add-event.name}. Offset {}, Message: {}", offset, message)

            val model = GitRepositoryModel()
            model.repositoryName = message.repositoryName
            model.ownerId = message.ownerId
            model.projectId = message.projectId
            model.spaceId = message.spaceId
            model.modifiedBy = message.ownerId
            model.createdBy = message.ownerId
            model.gitProviderId = referenceCache.getReferenceItemByTypeAndCode("GIT_PROVIDER", message.gitProviderCode).referenceItemId
            gitRepository.save(model)

            ack.acknowledge()
        } catch (e: Exception) {
            log.error("Got exception while adding repository", e)
            ack.nack(Duration.ofMillis(sleepTimeMs))
        }
    }

}