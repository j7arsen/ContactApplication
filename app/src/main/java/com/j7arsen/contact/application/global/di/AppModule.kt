package com.j7arsen.contact.application.global.di

import com.j7arsen.contact.application.global.event.EventDispatcher
import com.j7arsen.contact.application.global.message.SystemMessageNotifier
import com.j7arsen.contact.application.global.utils.ResourceProvider
import com.j7arsen.contact.application.global.utils.ValidatorUtil
import com.j7arsen.contact.application.global.utils.error.ErrorHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single { ErrorHandler(get()) }

    single { ResourceProvider(androidContext()) }

    single { SystemMessageNotifier() }

    single { EventDispatcher() }

    single { ValidatorUtil() }

}