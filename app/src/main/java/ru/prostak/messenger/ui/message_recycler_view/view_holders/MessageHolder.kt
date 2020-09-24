package ru.prostak.messenger.ui.message_recycler_view.view_holders

import androidx.recyclerview.widget.RecyclerView
import ru.prostak.messenger.ui.message_recycler_view.views.MessageView

interface MessageHolder {
    fun drawMessage(view: MessageView)
    fun onAttach(view: MessageView)
    fun onDetach()
}