package com.j7arsen.contact.application.data.model.mapper

interface IMapper<ENTITY, CACHE, MODEL> {

    fun mapEntityToModel(origin: ENTITY): MODEL

    fun mapEntityToModel(origin: List<ENTITY>): List<MODEL> {
        return origin.map(::mapEntityToModel)
    }

    fun mapEntityToCache(origin: ENTITY): CACHE

    fun mapEntityToCache(origin: List<ENTITY>): List<CACHE> {
        return origin.map(::mapEntityToCache)
    }

    fun mapCacheToModel(origin: CACHE): MODEL

    fun mapCacheToModel(origin: List<CACHE>): List<MODEL> {
        return origin.map(::mapCacheToModel)
    }

    fun mapModelToCache(origin: MODEL): CACHE

    fun mapModelToCache(origin: List<MODEL>): List<CACHE> {
        return origin.map(::mapModelToCache)
    }

}