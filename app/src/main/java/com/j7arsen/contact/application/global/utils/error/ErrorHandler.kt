package com.j7arsen.contact.application.global.utils.error

import com.j7arsen.contact.application.R
import com.j7arsen.contact.application.global.utils.ResourceProvider

class ErrorHandler(private val resourceProvider: ResourceProvider) {

    private val defaultErrorMessage = resourceProvider.getString(R.string.message_error_default)

    //возврат пока такой ошибки, если был бы запрос в сеть, то здесь был бы обработчик
    fun getError(throwable: Throwable): ErrorData {
        return ErrorData(ErrorData.ERROR_UNEXPECTED, defaultErrorMessage)
    }

}