package ru.prostak.messenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.prostak.messenger.MainActivity
import ru.prostak.messenger.R
import ru.prostak.messenger.database.AUTH
import ru.prostak.messenger.utilits.APP_ACTIVITY


open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        if (AUTH.currentUser != null)
            APP_ACTIVITY.mAppDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        if (AUTH.currentUser != null)
            APP_ACTIVITY.mAppDrawer.enableDrawer()
    }
}