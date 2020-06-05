package com.j7arsen.contact.application.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.j7arsen.contact.application.R
import com.j7arsen.contact.application.domain.model.ContactModel
import com.j7arsen.contact.application.global.base.BaseActivity
import com.j7arsen.contact.application.global.base.BaseFragment
import com.j7arsen.contact.application.global.utils.IBackButtonListener
import com.j7arsen.contact.application.global.utils.ResourceProvider
import com.j7arsen.contact.application.global.utils.Screens
import com.j7arsen.contact.application.global.utils.error.ErrorData
import com.j7arsen.contact.application.global.view.ProgressView
import com.j7arsen.contact.application.presentation.list.adapter.ContactListAdapter
import kotlinx.android.synthetic.main.fragment_contact_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.Router

class ContactListFragment : BaseFragment(), IBackButtonListener {

    private val router: Router by inject()

    private val resourceProvider: ResourceProvider by inject()

    override val layoutId: Int = R.layout.fragment_contact_list

    private val viewModel: ContactListViewModel by viewModel()

    private var deleteContactDialog: AlertDialog? = null

    private val contactListAdapter =
        ContactListAdapter(onDeleteClick = { viewModel.showDeleteContactDialog(it) },
            onItemCLick = { openContactDetailScreen(it) })

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
        initAdapter()
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
            baseActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            baseActivity.supportActionBar!!.setDisplayShowHomeEnabled(false)
        }
        currentToolbar.title = resourceProvider.getString(R.string.contact_list_screen_title)
    }

    private fun initAdapter() {
        rv_contact_list.apply {
            layoutManager = LinearLayoutManager(context!!)
            adapter = contactListAdapter
        }
    }

    private fun initListener() {
        pv_contact_list.setOnRetryListener(object : ProgressView.OnRetryListener {
            override fun onRetry() {
                viewModel.loadContactList(false)
            }
        })
        srl_contact_list.setOnRefreshListener {
            viewModel.loadContactList(true)
        }
    }

    private fun initObserver() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ContactListViewModel.ContactListViewModelState.ShowLoading -> showLoading(it.isRefreshState)
                is ContactListViewModel.ContactListViewModelState.CompleteLoading -> completeLoading(
                    it.isRefreshState
                )
                is ContactListViewModel.ContactListViewModelState.ErrorLoading -> errorLoading(it.errorData)
                is ContactListViewModel.ContactListViewModelState.ShowEmptyList -> showEmptyList()
                is ContactListViewModel.ContactListViewModelState.HideEmptyList -> hideEmptyList()
            }
        })
        viewModel.contactModelList.observe(viewLifecycleOwner, Observer {
            contactListAdapter.setData(it)
        })
        viewModel.deleteContactDialogState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ContactListViewModel.DeleteContactDialogState.ShowDialog -> showDeleteContactDialog(
                    it.contactModel
                )
                is ContactListViewModel.DeleteContactDialogState.HideDialog -> hideDeleteContactDialog()
            }
        })
    }

    private fun showLoading(isRefreshState: Boolean) {
        if (isRefreshState) {
            postViewAction {
                srl_contact_list.apply {
                    if (!isRefreshing) {
                        isRefreshing = true
                    }
                }
            }
        } else {
            cl_contact_list_container.visibility = View.GONE
            pv_contact_list.startLoading()
        }
    }

    private fun completeLoading(isRefreshState: Boolean) {
        if (isRefreshState) {
            postViewAction {
                srl_contact_list.apply {
                    if (isRefreshing) {
                        isRefreshing = false
                    }
                }
            }
        } else {
            cl_contact_list_container.visibility = View.VISIBLE
            pv_contact_list.completeLoading()
        }
    }

    private fun errorLoading(errorData: ErrorData) {
        cl_contact_list_container.visibility = View.GONE
        pv_contact_list.errorLoading(errorMessage = errorData.errorMessage)
    }

    private fun showEmptyList() {
        rv_contact_list.visibility = View.GONE
        sv_contact_list.visibility = View.VISIBLE
    }

    private fun hideEmptyList() {
        sv_contact_list.visibility = View.GONE
        rv_contact_list.visibility = View.VISIBLE
    }

    private fun showDeleteContactDialog(contactModel: ContactModel) {
        if (deleteContactDialog == null) {
            deleteContactDialog = AlertDialog.Builder(context!!).apply {
                setTitle(resourceProvider.getString(R.string.contact_delete_title))
                setMessage(resourceProvider.getString(R.string.contact_delete_message))
                setPositiveButton(resourceProvider.getString(R.string.ok)) { _, _ ->
                    viewModel.closeDeleteContactDialog()
                    viewModel.deleteContact(contactModel)
                }
                setNegativeButton(resourceProvider.getString(R.string.cancel)) { _, _ ->
                    viewModel.closeDeleteContactDialog()
                }
            }.create()
            deleteContactDialog?.setCanceledOnTouchOutside(false)
            deleteContactDialog?.show()
        }
    }

    private fun hideDeleteContactDialog() {
        if (deleteContactDialog != null && deleteContactDialog!!.isShowing) {
            deleteContactDialog!!.dismiss()
            deleteContactDialog = null
        }
    }

    private fun openContactDetailScreen(id: Long) {
        router.navigateTo(Screens.ContactDetailScreen(id))
    }

    override fun onDestroy() {
        super.onDestroy()
        hideDeleteContactDialog()
    }

    companion object {

        const val DELETE_CONTACT_DIALOG_TAG = "DELETE_CONTACT_DIALOG_TAG_"

        fun newInstance() = ContactListFragment()

    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }
}