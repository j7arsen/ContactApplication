package com.j7arsen.contact.application.global.utils.error

data class ErrorData(val errorType : Int, val errorMessage : String, val errorHttpCode : Int? = null, val errorCode : Int? = null) {

    companion object{
        const val ERROR_UNEXPECTED = 100001
    }

}