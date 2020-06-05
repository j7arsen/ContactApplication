package com.j7arsen.contact.application.data.model.api

data class ContactEntity(
    val id : Long,
    val firstName: String,
    val lastName: String,
    val photo: String?,
    val email: String
)