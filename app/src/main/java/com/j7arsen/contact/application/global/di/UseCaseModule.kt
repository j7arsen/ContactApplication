package com.j7arsen.contact.application.global.di

import com.j7arsen.contact.application.domain.interactor.DeleteContactUseCase
import com.j7arsen.contact.application.domain.interactor.GetContactDetailUseCase
import com.j7arsen.contact.application.domain.interactor.GetContactListUseCase
import com.j7arsen.contact.application.domain.interactor.UpdateContactUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { GetContactListUseCase(get()) }
    single { GetContactDetailUseCase(get()) }
    single { DeleteContactUseCase(get()) }
    single { UpdateContactUseCase(get()) }

}