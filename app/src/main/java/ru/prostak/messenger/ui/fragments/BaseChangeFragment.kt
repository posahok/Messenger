package ru.prostak.messenger.ui.fragments

import android.view.*
import androidx.fragment.app.Fragment
import ru.prostak.messenger.MainActivity
import ru.prostak.messenger.R
import ru.prostak.messenger.utilits.APP_ACTIVITY
import ru.prostak.messenger.utilits.hideKeyboard

open class BaseChangeFragment(layout: Int) : Fragment(layout) {
    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        if (activity is MainActivity)
            APP_ACTIVITY.mAppDrawer.disableDrawer()
        hideKeyboard()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_confirm_change -> {
                change()
            }
        }
        return true
    }

    open fun change() {
    }
}