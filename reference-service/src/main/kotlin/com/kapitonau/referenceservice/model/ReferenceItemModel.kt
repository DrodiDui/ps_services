package com.kapitonau.referenceservice.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "reference_item")
open class ReferenceItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reference_item_id_gen")
    @SequenceGenerator(
        name = "reference_item_id_gen",
        sequenceName = "reference_item_reference_item_id_seq",
        allocationSize = 1
    )
    @Column(name = "reference_item_id", nullable = false)
    open var referenceItemId: Long? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reference_type", nullable = false)
    open var referenceType: ReferencesTypeModel? = null

    @Column(name = "item_code", nullable = false, length = Integer.MAX_VALUE)
    open var itemCode: String? = null

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "description", nullable = false)
    open var description: Map<String, String>? = null
}