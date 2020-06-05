package com.j7arsen.contact.application.global.di

import com.j7arsen.contact.application.data.api.IApi
import com.j7arsen.contact.application.data.api.ApiSimulator
import org.koin.dsl.module

val apiModule = module {
    single<IApi>{
        ApiSimulator()
    }
}