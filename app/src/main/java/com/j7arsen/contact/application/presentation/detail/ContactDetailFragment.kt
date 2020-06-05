package com.j7arsen.contact.application.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.j7arsen.contact.application.R
import com.j7arsen.contact.application.domain.model.ContactModel
import com.j7arsen.contact.application.global.base.BaseActivity
import com.j7arsen.contact.application.global.base.BaseFragment
import com.j7arsen.contact.application.global.utils.IBackButtonListener
import com.j7arsen.contact.application.global.utils.ResourceProvider
import com.j7arsen.contact.application.global.utils.error.ErrorData
import com.j7arsen.contact.application.global.view.ProgressView
import com.j7arsen.contact.application.presentation.detail.validator.ContactDetailValidator
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_contact_detail.*
import kotlinx.android.synthetic.main.fragment_contact_list.toolbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.terrakok.cicerone.Router

class ContactDetailFragment : BaseFragment(), IBackButtonListener {

    private val router: Router by inject()

    private val resourceProvider: ResourceProvider by inject()

    override val layoutId: Int = R.layout.fragment_contact_detail

    private val viewModel: ContactDetailViewModel by viewModel {
        parametersOf(
            arguments?.getLong(
                CONTACT_ID
            )
        )
    }

    private var updateContactDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initListener()
        initObserver()
    }

    private fun initToolbar() {
        val baseActivity = activity as BaseActivity
        val currentToolbar = toolbar as Toolbar
        currentToolbar.title = ""
        baseActivity.setSupportActionBar(currentToolbar)
        if (baseActivity.supportActionBar != null) {
            baseActivity.supportActionBar!!.setDisplayShowTitleEnabled(true)
            baseActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            baseActivity.supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        currentToolbar.title =
            resourceProvider.getString(R.string.contact_detail_screen_title)
        currentToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initListener() {
        pv_contact_detail.setOnRetryListener(object : ProgressView.OnRetryListener {
            override fun onRetry() {
                viewModel.loadContactDetail()
            }
        })
        btn_edit_cancel.setOnClickListener { viewModel.changeMode() }
        btn_contact_detail_save.setOnClickListener {
            viewModel.updateContact(
                et_contact_detail_first_name.text.toString(),
                et_contact_detail_last_name.text.toString(),
                et_contact_detail_email.text.toString()
            )
        }
    }

    private fun initObserver() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ContactDetailViewModel.ContactDetailState.ShowLoading -> showLoading()
                is ContactDetailViewModel.ContactDetailState.CompleteLoading -> completeLoading()
                is ContactDetailViewModel.ContactDetailState.ErrorLoading -> errorLoading(it.errorData)
                is ContactDetailViewModel.ContactDetailState.Validation -> validationHandler(it.validator)
            }
        })
        viewModel.contactDetailMode.observe(viewLifecycleOwner, Observer {
            initViewEnabled(it)
        })
        viewModel.updateContactDialogState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ContactDetailViewModel.UpdateContactDialogState.ShowDialog -> showUpdateContactDialog()
                is ContactDetailViewModel.UpdateContactDialogState.HideDialog -> hideUpdateContactDialog()
            }
        })
        viewModel.contactModel.observe(viewLifecycleOwner, Observer { initContactModel(it) })
    }

    private fun initContactModel(contactModel: ContactModel) {
        if (!contactModel.photo.isNullOrEmpty()) {
            Picasso.get()
                .load(contactModel.photo!!)
                .transform(CropCircleTransformation())
                .placeholder(android.R.drawable.ic_dialog_info)
                .error(android.R.drawable.ic_dialog_info)
                .into(iv_contact_detail_img)
        } else {
            iv_contact_detail_img.setImageResource(android.R.drawable.ic_dialog_info)
        }
        et_contact_detail_first_name.setText(contactModel.firstName)
        et_contact_detail_last_name.setText(contactModel.lastName)
        et_contact_detail_email.setText(contactModel.email)
    }

    private fun initViewEnabled(contactDetailMode: ContactDetailViewModel.ContactDetailMode) {
        et_contact_detail_first_name.isEnabled =
            contactDetailMode == ContactDetailViewModel.ContactDetailMode.EDIT
        et_contact_detail_last_name.isEnabled =
            contactDetailMode == ContactDetailViewModel.ContactDetailMode.EDIT
        et_contact_detail_email.isEnabled =
            contactDetailMode == ContactDetailViewModel.ContactDetailMode.EDIT
        if (contactDetailMode == ContactDetailViewModel.ContactDetailMode.EDIT) {
            btn_edit_cancel.text = resourceProvider.getString(R.string.cancel)
            btn_contact_detail_save.visibility = View.VISIBLE
        } else {
            btn_edit_cancel.text = resourceProvider.getString(R.string.edit)
            btn_contact_detail_save.visibility = View.GONE
        }
    }

    private fun validationHandler(validator: ContactDetailValidator) {
        with(validator) {
            showHideErrorForField(et_contact_detail_first_name, firstNameMessage)
            showHideErrorForField(et_contact_detail_last_name, lastNameMessage)
            showHideErrorForField(et_contact_detail_email, emailMessage)
        }
    }

    private fun showHideErrorForField(editText: AppCompatEditText, param: String?) {
        if (param == null) {
            editText.error = null
        } else {
            editText.error = param
        }
    }

    private fun showLoading() {
        sv_contact_detail_container.visibility = View.GONE
        pv_contact_detail.startLoading()
    }

    private fun completeLoading() {
        sv_contact_detail_container.visibility = View.VISIBLE
        pv_contact_detail.completeLoading()
    }

    private fun errorLoading(errorData: ErrorData) {
        sv_contact_detail_container.visibility = View.GONE
        pv_contact_detail.errorLoading(errorMessage = errorData.errorMessage)
    }

    private fun showUpdateContactDialog() {
        if (updateContactDialog == null) {
            updateContactDialog = AlertDialog.Builder(context!!).apply {
                setTitle(resourceProvider.getString(R.string.contact_update_title))
                setMessage(resourceProvider.getString(R.string.contact_update_message))
                setPositiveButton(resourceProvider.getString(R.string.ok)) { _, _ ->
                    viewModel.closeUpdateContactDialog()
                }
            }.create()
            updateContactDialog?.setCanceledOnTouchOutside(false)
            updateContactDialog?.show()
        }
    }

    private fun hideUpdateContactDialog() {
        if (updateContactDialog != null && updateContactDialog!!.isShowing) {
            updateContactDialog!!.dismiss()
            updateContactDialog = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        hideUpdateContactDialog()
    }

    companion object {

        const val CONTACT_ID = "ContactDetailFragment.CONTACT_ID"

        fun newInstance(contactId: Long) = ContactDetailFragment().apply {
            arguments = Bundle().apply {
                putLong(
                    CONTACT_ID, contactId
                )
            }
        }

    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

}