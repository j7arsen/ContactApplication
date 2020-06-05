package com.j7arsen.contact.application.domain.repository

import com.j7arsen.contact.application.domain.model.ContactModel
import kotlinx.coroutines.flow.Flow

interface IContactRepository {

    fun getContactList(isRefreshState : Boolean): Flow<List<ContactModel>>

    fun getContactDetail(id: Long): Flow<ContactModel>

    fun updateContact(contactModel: ContactModel) : Flow<Unit>

    fun deleteContact(contactModel: ContactModel) : Flow<Unit>

}