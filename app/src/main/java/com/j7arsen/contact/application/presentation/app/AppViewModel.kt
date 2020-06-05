package com.j7arsen.contact.application.presentation.app

import androidx.lifecycle.LiveData
import com.j7arsen.contact.application.global.base.BaseViewModel
import com.j7arsen.contact.application.global.utils.SingleLiveEvent

class AppViewModel : BaseViewModel() {

    private val _openRootScreen : SingleLiveEvent<Unit> = SingleLiveEvent()
    val openRootScreen : LiveData<Unit> = _openRootScreen

    init {
        _openRootScreen.call()
    }

}