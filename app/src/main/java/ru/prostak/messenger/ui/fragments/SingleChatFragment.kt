package ru.prostak.messenger.ui.fragments

import android.view.View
import kotlinx.android.synthetic.main.activity_main.view.*
import ru.prostak.messenger.R
import ru.prostak.messenger.models.CommonModel
import ru.prostak.messenger.utilits.APP_ACTIVITY

class SingleChatFragment(contact: CommonModel) : BaseFragment(R.layout.fragment_single_chat) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.mToolbar.toolbar_info.visibility = View.VISIBLE

    }

    override fun onPause() {
        super.onPause()
        APP_ACTIVITY.mToolbar.toolbar_info.visibility = View.GONE
    }
}