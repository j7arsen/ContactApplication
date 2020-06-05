package com.j7arsen.contact.application.global.utils

import java.util.regex.Pattern

class ValidatorUtil {

    companion object {

        const val FIRST_NAME_MIN_LENGTH = 2
        const val LAST_NAME_MIN_LENGTH = 2

    }

    private val emailPattern: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+")

    fun emailValid(email : String) : Boolean{
        return email.isNotEmpty() && emailPattern.matcher(email).matches()
    }

    fun firstNameValid(firstName : String) : Boolean{
        return firstName.isNotEmpty() && firstName.length >= FIRST_NAME_MIN_LENGTH
    }

    fun lastNameValid(lastName : String) : Boolean{
        return lastName.isNotEmpty() && lastName.length >= LAST_NAME_MIN_LENGTH
    }

}