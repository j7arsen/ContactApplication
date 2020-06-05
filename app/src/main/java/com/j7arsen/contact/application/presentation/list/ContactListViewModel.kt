package com.j7arsen.contact.application.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.j7arsen.contact.application.R
import com.j7arsen.contact.application.domain.dispatcher.DispatcherFacade
import com.j7arsen.contact.application.domain.interactor.DeleteContactUseCase
import com.j7arsen.contact.application.domain.interactor.GetContactListUseCase
import com.j7arsen.contact.application.domain.model.ContactModel
import com.j7arsen.contact.application.global.base.BaseViewModel
import com.j7arsen.contact.application.global.event.EventDispatcher
import com.j7arsen.contact.application.global.event.EventType
import com.j7arsen.contact.application.global.message.SystemMessageNotifier
import com.j7arsen.contact.application.global.utils.ResourceProvider
import com.j7arsen.contact.application.global.utils.error.ErrorData
import com.j7arsen.contact.application.global.utils.error.ErrorHandler
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class ContactListViewModel(
    private val errorHandler: ErrorHandler,
    private val dispatcherFactory: DispatcherFacade,
    private val resourceProvider: ResourceProvider,
    private val systemMessageNotifier: SystemMessageNotifier,
    private val eventDispatcher: EventDispatcher,
    private val getContactListUseCase: GetContactListUseCase,
    private val deleteContactUseCase: DeleteContactUseCase
) : BaseViewModel(), EventDispatcher.EventListener, CoroutineScope {

    private val _screenState: MutableLiveData<ContactListViewModelState> = MutableLiveData()
    val screenState: LiveData<ContactListViewModelState> = _screenState

    private val _contactModelList: MutableLiveData<List<ContactModel>> = MutableLiveData()
    val contactModelList: LiveData<List<ContactModel>> = _contactModelList

    private val _deleteContactDialogState: MutableLiveData<DeleteContactDialogState> =
        MutableLiveData()
    val deleteContactDialogState: LiveData<DeleteContactDialogState> = _deleteContactDialogState

    private val job: Job = SupervisorJob()
    private val handler = CoroutineExceptionHandler { _, exception ->
        _screenState.value =
            ContactListViewModelState.ErrorLoading(errorHandler.getError(exception))
    }

    override val coroutineContext: CoroutineContext
        get() = handler + job + Dispatchers.Main

    init {
        this.eventDispatcher.addEventListener(EventType.UPDATE_CONTACT, listener = this)
        loadContactList(false)
    }

    override fun onEvent(eventType: EventType, data: Any?) {
        when (eventType) {
            EventType.UPDATE_CONTACT -> loadContactList(false)
        }
    }

    @ExperimentalCoroutinesApi
    fun loadContactList(isRefreshState: Boolean) {
        launch {
            getContactListUseCase.execute(dispatcherFactory, params = isRefreshState)
                .onStart {
                    if (!isRefreshState) {
                        _screenState.value = ContactListViewModelState.HideEmptyList
                    }
                    _screenState.value =
                        ContactListViewModelState.ShowLoading(isRefreshState = isRefreshState)
                }
                .collect {
                    _screenState.value =
                        ContactListViewModelState.CompleteLoading(isRefreshState = isRefreshState)
                    if (it.isNotEmpty()) {
                        _contactModelList.value = it
                    } else {
                        _screenState.value = ContactListViewModelState.ShowEmptyList
                    }
                }
        }
    }

    @ExperimentalCoroutinesApi
    fun deleteContact(contactModel: ContactModel) {
        launch {
            deleteContactUseCase.execute(dispatcherFactory, params = contactModel)
                .collect {
                    systemMessageNotifier.send(resourceProvider.getString(R.string.contact_delete_success))
                    loadContactList(false)
                }
        }
    }

    fun showDeleteContactDialog(contactModel: ContactModel) {
        _deleteContactDialogState.value = DeleteContactDialogState.ShowDialog(contactModel)
    }

    fun closeDeleteContactDialog() {
        _deleteContactDialogState.value = DeleteContactDialogState.HideDialog
    }

    override fun onCleared() {
        super.onCleared()
        if (coroutineContext.isActive) {
            coroutineContext.cancel()
        }
        eventDispatcher.removeEventListener(this)
    }

    sealed class ContactListViewModelState {

        data class ShowLoading(val isRefreshState: Boolean) : ContactListViewModelState()

        data class CompleteLoading(val isRefreshState: Boolean) : ContactListViewModelState()

        data class ErrorLoading(val errorData: ErrorData) : ContactListViewModelState()

        object ShowEmptyList : ContactListViewModelState()

        object HideEmptyList : ContactListViewModelState()

    }

    sealed class DeleteContactDialogState {

        data class ShowDialog(val contactModel: ContactModel) : DeleteContactDialogState()

        object HideDialog : DeleteContactDialogState()

    }

}
