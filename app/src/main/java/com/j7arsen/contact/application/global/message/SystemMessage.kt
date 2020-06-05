package com.j7arsen.contact.application.global.message

data class SystemMessage(
    val text: String,
    val type: SystemMessageType = SystemMessageType.TOAST
)
