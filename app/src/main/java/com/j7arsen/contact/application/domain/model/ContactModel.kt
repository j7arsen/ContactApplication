package com.j7arsen.contact.application.domain.model

data class ContactModel(
    val id : Long,
    var firstName: String,
    var lastName: String,
    val photo: String?,
    var email: String
)