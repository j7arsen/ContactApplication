package com.j7arsen.contact.application.domain.interactor.base

import com.j7arsen.contact.application.domain.dispatcher.DispatcherFacade
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<T, in Params>{

    protected abstract fun buildUseCaseObservable(params: Params? = null): Flow<T>

    @ExperimentalCoroutinesApi
    open fun execute(dispatcherFacade: DispatcherFacade? = null, params: Params? = null): Flow<T> {
        return if(dispatcherFacade != null){
            buildUseCaseObservable(params).flowOn(dispatcherFacade.dispatcher)
        } else{
            buildUseCaseObservable(params)
        }
    }

}