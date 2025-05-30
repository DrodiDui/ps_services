package com.kapitonau.referenceservice.controller

import com.kapitonau.projectstudio.referenceservice.api.ReferenceItemApi
import com.kapitonau.projectstudio.referenceservice.dto.ReferenceItemResponse
import com.kapitonau.referenceservice.service.ReferenceItemService
import org.springframework.web.bind.annotation.RestController

@RestController
class ReferenceItemController(
    private val referenceItemService: ReferenceItemService
) : ReferenceItemApi {
    override fun getReferenceItemById(referenceItemId: Long): ReferenceItemResponse =
        referenceItemService.geReferenceItemById(referenceItemId)

    override fun getAllReferenceItems(
        referenceType: String,
        itemCode: String?
    ): List<ReferenceItemResponse?>? =
        referenceItemService.getReferenceByReferenceTypeAndItemCode(referenceType, itemCode)
}