package ru.prostak.messenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.prostak.messenger.MainActivity
import ru.prostak.messenger.R


open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        if (activity is MainActivity)
            (activity as MainActivity).mAppDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        if (activity is MainActivity)
            (activity as MainActivity).mAppDrawer.enableDrawer()
    }
}