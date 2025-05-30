package com.kapitonau.referenceservice.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "references_type")
open class ReferencesTypeModel {
    @Id
    @SequenceGenerator(
        name = "references_type_id_gen",
        sequenceName = "reference_item_reference_item_id_seq",
        allocationSize = 1
    )
    @Column(name = "reference_type", nullable = false, length = Integer.MAX_VALUE)
    open var referenceType: String? = null

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "description", nullable = false)
    open var description: Map<String, String>? = null
}