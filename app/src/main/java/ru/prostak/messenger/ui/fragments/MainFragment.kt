package ru.prostak.messenger.ui.fragments

import androidx.fragment.app.Fragment
import ru.prostak.messenger.R
import ru.prostak.messenger.utilits.APP_ACTIVITY
import ru.prostak.messenger.utilits.hideKeyboard

class MainFragment : Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Чаты"
        hideKeyboard()
    }

}