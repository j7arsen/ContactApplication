package com.j7arsen.contact.application.global.di

import com.j7arsen.contact.application.data.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single { AppDatabase.getInstance(androidContext()) }
    single{ get<AppDatabase>().contactDao() }
}