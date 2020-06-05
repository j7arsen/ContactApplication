package com.j7arsen.contact.application.global.di

import com.j7arsen.contact.application.data.repository.ContactRepository
import com.j7arsen.contact.application.domain.repository.IContactRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<IContactRepository> { ContactRepository(get(), get(), get()) }

}