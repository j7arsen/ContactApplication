package com.j7arsen.contact.application.global.utils

import androidx.fragment.app.Fragment
import com.j7arsen.contact.application.presentation.detail.ContactDetailFragment
import com.j7arsen.contact.application.presentation.list.ContactListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object ContactListScreen : SupportAppScreen(){
        override fun getFragment(): Fragment? = ContactListFragment.newInstance()
    }

    data class ContactDetailScreen(val contactId : Long) : SupportAppScreen(){
        override fun getFragment(): Fragment? = ContactDetailFragment.newInstance(contactId)
    }

}