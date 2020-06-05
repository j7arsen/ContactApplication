package com.j7arsen.contact.application.global.di

import com.j7arsen.contact.application.data.model.api.ContactEntity
import com.j7arsen.contact.application.data.model.cache.ContactCache
import com.j7arsen.contact.application.data.model.mapper.IMapper
import com.j7arsen.contact.application.data.repository.mapper.ContactMapper
import com.j7arsen.contact.application.domain.model.ContactModel
import org.koin.dsl.module

val mapperModule = module {

    single<IMapper<ContactEntity, ContactCache, ContactModel>> { ContactMapper() }

}