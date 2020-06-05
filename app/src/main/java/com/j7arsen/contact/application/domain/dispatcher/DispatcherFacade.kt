package com.j7arsen.contact.application.domain.dispatcher

import kotlin.coroutines.CoroutineContext

interface DispatcherFacade{

    val dispatcher : CoroutineContext

}