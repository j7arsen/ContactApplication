package com.j7arsen.contact.application.presentation.app

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.j7arsen.contact.application.R
import com.j7arsen.contact.application.global.base.BaseContainerActivity
import com.j7arsen.contact.application.global.message.SystemMessageNotifier
import com.j7arsen.contact.application.global.message.SystemMessageType
import com.j7arsen.contact.application.global.utils.IBackButtonListener
import com.j7arsen.contact.application.global.utils.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class AppActivity : BaseContainerActivity(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private var notifierJob: Job? = null

    private val systemMessageNotifier: SystemMessageNotifier by inject()

    private val router : Router by inject()

    private val navigatorHolder: NavigatorHolder by inject()

    private val viewModel : AppViewModel by viewModel()

    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.fl_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openRootScreen.observe(this, Observer { router.newRootScreen(Screens.ContactListScreen) })
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        subscribeOnSystemMessages()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        unsubscribeOnSystemMessages()
        super.onPause()
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun subscribeOnSystemMessages() {
        notifierJob = systemMessageNotifier.notifier
            .onEach { msg ->
                when (msg.type) {
                    SystemMessageType.TOAST -> showToastMessage(msg.text)
                }
            }
            .launchIn(this)
    }

    private fun unsubscribeOnSystemMessages() {
        notifierJob?.cancel()
    }

    override fun onBackPressed() {
        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fl_container)
        if (fragment != null && fragment is IBackButtonListener
            && (fragment as IBackButtonListener).onBackPressed()
        ) {
            return
        } else {
            super.onBackPressed()
        }
    }
}