package com.j7arsen.contact.application.global.base

import android.os.Handler
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(){

    private val viewHandler = Handler()

    abstract val layoutId : Int

    protected fun postViewAction(action: () -> Unit) {
        viewHandler.post(action)
    }

}