package com.j7arsen.contact.application.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.j7arsen.contact.application.R
import com.j7arsen.contact.application.domain.dispatcher.DispatcherFacade
import com.j7arsen.contact.application.domain.interactor.GetContactDetailUseCase
import com.j7arsen.contact.application.domain.interactor.UpdateContactUseCase
import com.j7arsen.contact.application.domain.model.ContactModel
import com.j7arsen.contact.application.global.base.BaseViewModel
import com.j7arsen.contact.application.global.event.EventDispatcher
import com.j7arsen.contact.application.global.event.EventType
import com.j7arsen.contact.application.global.message.SystemMessageNotifier
import com.j7arsen.contact.application.global.utils.ResourceProvider
import com.j7arsen.contact.application.global.utils.ValidatorUtil
import com.j7arsen.contact.application.global.utils.error.ErrorData
import com.j7arsen.contact.application.global.utils.error.ErrorHandler
import com.j7arsen.contact.application.global.utils.notifyObserver
import com.j7arsen.contact.application.presentation.detail.validator.ContactDetailValidator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class ContactDetailViewModel(
    private val errorHandler: ErrorHandler,
    private val dispatcherFactory: DispatcherFacade,
    private val resourceProvider: ResourceProvider,
    private val systemMessageNotifier: SystemMessageNotifier,
    private val eventDispatcher: EventDispatcher,
    private val getContactDetailUseCase: GetContactDetailUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val validatorUtil: ValidatorUtil,
    private val contactId: Long
) : BaseViewModel(), CoroutineScope {

    private val _screenState: MutableLiveData<ContactDetailState> = MutableLiveData()
    val screenState: LiveData<ContactDetailState> = _screenState

    private val _contactModel: MutableLiveData<ContactModel> = MutableLiveData()
    val contactModel: LiveData<ContactModel> = _contactModel

    private val _contactDetailMode: MutableLiveData<ContactDetailMode> =
        MutableLiveData(ContactDetailMode.INIT)
    val contactDetailMode: LiveData<ContactDetailMode> = _contactDetailMode

    private val _updateContactDialogState: MutableLiveData<UpdateContactDialogState> =
        MutableLiveData()
    val updateContactDialogState: LiveData<UpdateContactDialogState> = _updateContactDialogState

    private val job: Job = SupervisorJob()
    private val handler = CoroutineExceptionHandler { _, exception ->
        _screenState.value =
            ContactDetailState.ErrorLoading(errorHandler.getError(exception))
    }

    override val coroutineContext: CoroutineContext
        get() = handler + job + Dispatchers.Main

    init {
        loadContactDetail()
    }

    @ExperimentalCoroutinesApi
    fun loadContactDetail() {
        launch {
            getContactDetailUseCase.execute(dispatcherFactory, params = contactId)
                .onStart {
                    _screenState.value = ContactDetailState.ShowLoading
                }
                .collect {
                    _screenState.value = ContactDetailState.CompleteLoading
                    _contactModel.value = it
                }
        }
    }

    @ExperimentalCoroutinesApi
    fun makeUpdateContact(contactModel: ContactModel) {
        launch {
            updateContactUseCase.execute(dispatcherFactory, params = contactModel)
                .collect {
                    systemMessageNotifier.send(resourceProvider.getString(R.string.contact_update_success))
                    eventDispatcher.sendEvent(EventType.UPDATE_CONTACT)
                    changeMode()
                }
        }
    }

    fun updateContact(firstName: String, lastName: String, email: String) {
        if (checkUpdateContact(firstName, lastName, email)) {
            val contactDetailValidator = validateContact(firstName, lastName, email)
            _screenState.value = ContactDetailState.Validation(contactDetailValidator)
            if (contactDetailValidator.isValid) {
                val currentContactModel = _contactModel.value
                if (currentContactModel != null) {
                    with(currentContactModel) {
                        this.firstName = firstName
                        this.lastName = lastName
                        this.email = email
                        makeUpdateContact(this)
                    }
                }
            }
        } else {
            showUpdateContactDialog()
        }
    }

    private fun checkUpdateContact(firstName: String, lastName: String, email: String): Boolean {
        var isUpdate = false
        val currentContactModel = _contactModel.value
        if (currentContactModel != null) {
            with(currentContactModel) {
                if (this.firstName != firstName || this.lastName != lastName || this.email != email) {
                    isUpdate = true
                }
            }
        }
        return isUpdate
    }

    private fun validateContact(
        firstName: String,
        lastName: String,
        email: String
    ): ContactDetailValidator {
        var firstNameInvalidMessage: String? =
            resourceProvider.getString(R.string.contact_detail_first_name_invalid)
        var lastNameInvalidMessage: String? =
            resourceProvider.getString(R.string.contact_detail_last_name_invalid)
        var emailInvalidMessage: String? =
            resourceProvider.getString(R.string.contact_detail_email_invalid)
        if (validatorUtil.firstNameValid(firstName)) {
            firstNameInvalidMessage = null
        }
        if (validatorUtil.lastNameValid(lastName)) {
            lastNameInvalidMessage = null
        }
        if (validatorUtil.emailValid(email)) {
            emailInvalidMessage = null
        }
        return ContactDetailValidator(
            (firstNameInvalidMessage == null && lastNameInvalidMessage == null && emailInvalidMessage == null),
            firstNameInvalidMessage,
            lastNameInvalidMessage,
            emailInvalidMessage
        )
    }

    fun changeMode() {
        val mode = _contactDetailMode.value
        if (mode != null) {
            if (mode == ContactDetailMode.EDIT) {
                _contactDetailMode.value = ContactDetailMode.INIT
               _contactModel.notifyObserver()
            } else {
                _contactDetailMode.value = ContactDetailMode.EDIT
            }
        }
    }

    fun showUpdateContactDialog() {
        _updateContactDialogState.value = UpdateContactDialogState.ShowDialog
    }

    fun closeUpdateContactDialog() {
        _updateContactDialogState.value = UpdateContactDialogState.HideDialog
    }

    override fun onCleared() {
        super.onCleared()
        if (coroutineContext.isActive) {
            coroutineContext.cancel()
        }
    }

    sealed class ContactDetailState {

        object ShowLoading : ContactDetailState()


        object CompleteLoading : ContactDetailState()

        data class ErrorLoading(val errorData: ErrorData) : ContactDetailState()

        data class Validation(val validator: ContactDetailValidator) : ContactDetailState()

    }

    sealed class UpdateContactDialogState {

        object ShowDialog : UpdateContactDialogState()

        object HideDialog : UpdateContactDialogState()

    }

    enum class ContactDetailMode {
        INIT, EDIT
    }

}