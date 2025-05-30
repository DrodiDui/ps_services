package com.kapitonau.gitservice.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "settings")
open class SettingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "settings_id_gen")
    @SequenceGenerator(name = "settings_id_gen", sequenceName = "settings_setting_id_seq", allocationSize = 1)
    @Column(name = "setting_id", nullable = false)
    open var settingId: Long? = null

    @Column(name = "setting_name", nullable = false)
    open var settingName: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "repository_id", nullable = false)
    open var repository: GitRepositoryModel? = null

    @Column(name = "provider_setting", nullable = false)
    open var providerSetting: Long? = null

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "settings", nullable = false)
    open var settings: Map<String, Any>? = null
}