package com.j7arsen.contact.application.global.dispathers

import com.j7arsen.contact.application.domain.dispatcher.DispatcherFacade
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class MainDispatcher : DispatcherFacade {

    override val dispatcher: CoroutineContext
        get() = Dispatchers.Main
}