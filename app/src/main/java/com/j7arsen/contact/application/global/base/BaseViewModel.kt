package com.j7arsen.contact.application.global.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope, KoinComponent{

    private val job: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = handler + job + Dispatchers.Main

    abstract val exceptionHandler : ((Throwable) -> Unit)?

    private val handler = CoroutineExceptionHandler { _, exception ->
        exceptionHandler?.invoke(exception)
    }

    override fun onCleared() {
        super.onCleared()
        if (coroutineContext.isActive) {
            coroutineContext.cancel()
        }
    }

}