package ru.prostak.messenger.ui.fragments

import kotlinx.android.synthetic.main.fragment_enter_phone_number.*
import ru.prostak.messenger.R
import ru.prostak.messenger.utilits.replaceFragment
import ru.prostak.messenger.utilits.showToast

class EnterPhoneNumberFragment : BaseFragment(R.layout.fragment_enter_phone_number) {

    override fun onStart() {
        super.onStart()
        register_btn_next.setOnClickListener {
            sendCode()
        }
    }

    private fun sendCode() {
        if (register_input_phone_number.text.toString().isEmpty()){
            showToast(getString(R.string.register_toast_enter_phone))
        } else {
            replaceFragment(EnterCodeFragment())
        }
    }
}