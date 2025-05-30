package com.kapitonau.referenceservice.repository

import com.kapitonau.referenceservice.model.ReferenceItemModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ReferenceItemRepository : JpaRepository<ReferenceItemModel, Long>, CustomReferenceItemRepository {

    @Query(
        """
    SELECT i FROM ReferenceItemModel i 
    WHERE (:referenceType IS NULL OR i.referenceType.referenceType = :referenceType)
    AND (:itemCode IS NULL OR i.itemCode = :itemCode)
"""
    )
    fun findAllByReferenceTypeAndItemCode(
        @Param("referenceType") referenceType: String?,
        @Param("itemCode") itemCode: String?
    ): List<ReferenceItemModel>

}