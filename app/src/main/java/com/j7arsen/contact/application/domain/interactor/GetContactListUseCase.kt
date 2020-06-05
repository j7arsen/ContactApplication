package com.j7arsen.contact.application.domain.interactor

import com.j7arsen.contact.application.domain.interactor.base.BaseListUseCase
import com.j7arsen.contact.application.domain.model.ContactModel
import com.j7arsen.contact.application.domain.repository.IContactRepository
import kotlinx.coroutines.flow.Flow

class GetContactListUseCase(private val contactRepository: IContactRepository) : BaseListUseCase<ContactModel, Boolean>() {

    override fun buildUseCaseObservable(params: Boolean?): Flow<List<ContactModel>> = contactRepository.getContactList(params!!)

}