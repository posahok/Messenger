package ru.prostak.messenger.ui.screens

import kotlinx.android.synthetic.main.fragment_change_bio.*
import ru.prostak.messenger.R
import ru.prostak.messenger.database.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {
    override fun onResume() {
        super.onResume()
        settings_input_bio.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()
        setBioToDatabase(newBio)

    }
}