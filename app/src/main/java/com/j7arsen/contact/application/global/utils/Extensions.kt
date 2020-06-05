package com.j7arsen.contact.application.global.utils

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver(){
    this.value = this.value
}