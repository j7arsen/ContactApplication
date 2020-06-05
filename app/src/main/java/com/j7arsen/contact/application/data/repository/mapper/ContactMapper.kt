package com.j7arsen.contact.application.data.repository.mapper

import com.j7arsen.contact.application.data.model.api.ContactEntity
import com.j7arsen.contact.application.data.model.cache.ContactCache
import com.j7arsen.contact.application.data.model.mapper.IMapper
import com.j7arsen.contact.application.domain.model.ContactModel

class ContactMapper : IMapper<ContactEntity, ContactCache, ContactModel> {
    override fun mapEntityToModel(origin: ContactEntity): ContactModel = ContactModel(
        id = origin.id,
        firstName = origin.firstName,
        lastName = origin.lastName,
        photo = origin.photo,
        email = origin.email
    )

    override fun mapEntityToCache(origin: ContactEntity): ContactCache = ContactCache(
        id = origin.id,
        firstName = origin.firstName,
        lastName = origin.lastName,
        photo = origin.photo,
        email = origin.email
    )

    override fun mapCacheToModel(origin: ContactCache): ContactModel = ContactModel(
        id = origin.id,
        firstName = origin.firstName,
        lastName = origin.lastName,
        photo = origin.photo,
        email = origin.email
    )

    override fun mapModelToCache(origin: ContactModel): ContactCache = ContactCache(
        id = origin.id,
        firstName = origin.firstName,
        lastName = origin.lastName,
        photo = origin.photo,
        email = origin.email
    )
}


