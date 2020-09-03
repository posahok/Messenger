package ru.prostak.messenger.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import ru.prostak.messenger.MainActivity
import ru.prostak.messenger.R
import ru.prostak.messenger.activities.RegisterActivity
import ru.prostak.messenger.utilits.AUTH
import ru.prostak.messenger.utilits.replaceActivity

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_action_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
        }
        return true
    }
}