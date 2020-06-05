package com.j7arsen.contact.application.global.di

import com.j7arsen.contact.application.domain.dispatcher.DispatcherFacade
import com.j7arsen.contact.application.global.dispathers.DefaultDispatcher
import com.j7arsen.contact.application.global.dispathers.IODispatcher
import com.j7arsen.contact.application.global.dispathers.MainDispatcher
import com.j7arsen.contact.application.global.dispathers.UnconfinedDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatcherModule = module {
    single<DispatcherFacade>(qualifier = named("io")) { IODispatcher() }
    single<DispatcherFacade>(qualifier = named("main")) { MainDispatcher() }
    single<DispatcherFacade>(qualifier = named("unconfined")) { UnconfinedDispatcher() }
    single<DispatcherFacade>(qualifier = named("default")) { DefaultDispatcher() }
}