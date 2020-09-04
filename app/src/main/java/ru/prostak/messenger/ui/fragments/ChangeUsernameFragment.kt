package ru.prostak.messenger.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_change_username.*
import ru.prostak.messenger.R
import ru.prostak.messenger.utilits.*
import java.util.*


class ChangeUsernameFragment : BaseFragment(R.layout.fragment_change_username) {
    lateinit var mNewUsername: String
    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        settings_input_username.setText(USER.username)

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

    private fun change() {
        mNewUsername = settings_input_username.text.toString().toLowerCase(Locale.getDefault())
        if (mNewUsername.isEmpty()){
            showToast("Поле пустое")
        } else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES).addListenerForSingleValueEvent(AppValueEventListener{
                if (it.hasChild(mNewUsername)){
                    showToast("Такой пользователь уже существует")
                }else{
                    changeUsername()
                }
            })
        }

    }

    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername)
            .setValue(UID)
            .addOnCompleteListener {
            if (it.isSuccessful){
                updateCurrentUsername()
            }
        }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USERNAME)
            .setValue(mNewUsername)
            .addOnCompleteListener {
            if (it.isSuccessful){
                showToast(getString(R.string.toast_data_update))
                deleteOldUsername()
            } else {
                showToast(it.exception?.message.toString())
            }
        }
    }

    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username)
            .removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    fragmentManager?.popBackStack()
                    USER.username = mNewUsername
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }
}