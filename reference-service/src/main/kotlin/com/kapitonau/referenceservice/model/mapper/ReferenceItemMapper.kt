package com.kapitonau.referenceservice.model.mapper

import com.kapitonau.projectstudio.referenceservice.dto.ReferenceItemResponse
import com.kapitonau.referenceservice.model.ReferenceItemModel
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import java.util.*

@Component
class ReferenceItemMapper {
    fun map(model: ReferenceItemModel): ReferenceItemResponse {
        return ReferenceItemResponse(
            model.referenceItemId,
            model.referenceType!!.referenceType,
            model.itemCode,
            model.description!![DESCRIPTION_LANGUAGE]
        )
    }

    companion object {
        private const val DESCRIPTION_LANGUAGE = "ru"
    }
}
