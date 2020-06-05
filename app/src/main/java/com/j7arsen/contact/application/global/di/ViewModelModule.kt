package com.j7arsen.contact.application.global.di

import com.j7arsen.contact.application.presentation.app.AppViewModel
import com.j7arsen.contact.application.presentation.detail.ContactDetailViewModel
import com.j7arsen.contact.application.presentation.list.ContactListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        AppViewModel()
    }

    viewModel {
        ContactListViewModel(get(), get(qualifier = named("io")), get(), get(), get(), get(), get())
    }

    viewModel { (contactId: Long) ->
        ContactDetailViewModel(
            get(),
            get(qualifier = named("io")),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            contactId
        )
    }

}