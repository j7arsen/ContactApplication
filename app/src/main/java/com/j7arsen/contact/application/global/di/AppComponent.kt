package com.j7arsen.contact.application.global.di

import org.koin.core.module.Module

val appComponent: List<Module> = listOf(
    appModule,
    apiModule,
    dbModule,
    navigationModule,
    dispatcherModule,
    mapperModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)