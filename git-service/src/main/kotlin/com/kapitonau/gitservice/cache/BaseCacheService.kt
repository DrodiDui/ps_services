package com.kapitonau.gitservice.cache

interface BaseCacheService<T> {

    fun getByEntityId(entityId: Long): T

}