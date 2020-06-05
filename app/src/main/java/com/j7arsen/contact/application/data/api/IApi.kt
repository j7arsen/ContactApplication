package com.j7arsen.contact.application.data.api

import com.j7arsen.contact.application.data.model.api.ContactEntity

interface IApi {

    suspend fun getContactList () : List<ContactEntity>

}