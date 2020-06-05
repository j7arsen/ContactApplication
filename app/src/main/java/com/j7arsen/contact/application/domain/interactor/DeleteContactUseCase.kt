package com.j7arsen.contact.application.domain.interactor

import com.j7arsen.contact.application.domain.interactor.base.BaseUseCase
import com.j7arsen.contact.application.domain.model.ContactModel
import com.j7arsen.contact.application.domain.repository.IContactRepository
import kotlinx.coroutines.flow.Flow

class DeleteContactUseCase(private val contactRepository: IContactRepository) : BaseUseCase<Unit, ContactModel>() {

    override fun buildUseCaseObservable(params: ContactModel?): Flow<Unit> = contactRepository.deleteContact(params!!)

}