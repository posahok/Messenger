package ru.prostak.messenger.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_enter_code.*
import ru.prostak.messenger.R
import ru.prostak.messenger.utilits.AppTextWatcher
import ru.prostak.messenger.utilits.showToast

class EnterCodeFragment : BaseFragment(R.layout.fragment_enter_code) {
    override fun onStart() {
        super.onStart()
        register_input_code.addTextChangedListener(AppTextWatcher {
            val string = it.toString()
            if (string.length == 6) {
                verificateCode()
            }
        })
    }

    private fun verificateCode() {
        showToast("Ok")
    }
}