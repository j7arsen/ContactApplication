package com.j7arsen.contact.application.global.base

import com.j7arsen.contact.application.R

abstract class BaseContainerActivity : BaseActivity(){

    override val layoutId: Int
        get() = R.layout.activity_container

}