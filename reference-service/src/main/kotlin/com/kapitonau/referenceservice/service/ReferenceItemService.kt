package com.kapitonau.referenceservice.service

import com.kapitonau.commonspring.exception.CommonServiceException
import com.kapitonau.projectstudio.referenceservice.dto.ReferenceItemResponse
import com.kapitonau.referenceservice.model.mapper.ReferenceItemMapper
import com.kapitonau.referenceservice.repository.ReferenceItemRepository
import org.springframework.stereotype.Service

@Service
class ReferenceItemService(
    private val referenceItemRepository: ReferenceItemRepository,
    private val referenceItemMapper: ReferenceItemMapper
) {

    fun geReferenceItemById(referenceItemId: Long): ReferenceItemResponse {
        return referenceItemRepository.findById(referenceItemId)
            .map { it -> referenceItemMapper.map(it) }
            .orElseThrow { CommonServiceException("REFERENCE_SERVICE", "Reference item not found") }
    }

    fun getReferenceByReferenceTypeAndItemCode(
        referenceType: String,
        itemCode: String?
    ): List<ReferenceItemResponse?>? {
        return referenceItemRepository.findAllByReferenceTypeAndItemCode(referenceType, itemCode)
            .map { it -> referenceItemMapper.map(it) }
            .toList()
    }

}
