package com.j7arsen.contact.application.domain.interactor

import com.j7arsen.contact.application.domain.interactor.base.BaseUseCase
import com.j7arsen.contact.application.domain.model.ContactModel
import com.j7arsen.contact.application.domain.repository.IContactRepository
import kotlinx.coroutines.flow.Flow

class GetContactDetailUseCase(private val contactRepository: IContactRepository) : BaseUseCase<ContactModel, Long>() {

    override fun buildUseCaseObservable(params: Long?): Flow<ContactModel> = contactRepository.getContactDetail(params!!)

}