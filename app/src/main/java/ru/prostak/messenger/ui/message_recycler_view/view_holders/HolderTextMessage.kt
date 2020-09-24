package ru.prostak.messenger.ui.message_recycler_view.view_holders

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_item_text.view.*
import ru.prostak.messenger.database.CURRENT_UID
import ru.prostak.messenger.ui.message_recycler_view.views.MessageView
import ru.prostak.messenger.utilits.asTime

class HolderTextMessage(view: View): RecyclerView.ViewHolder(view), MessageHolder {

    private val blocUserMessage: ConstraintLayout = view.bloc_user_message
    private val chatUserMessage: TextView = view.chat_user_message
    private val chatUserMessageTime: TextView = view.chat_user_message_time

    private val blocReceivedMessage: ConstraintLayout = view.bloc_received_message
    private val chatReceivedMessage: TextView = view.chat_received_message
    private val chatReceivedMessageTime: TextView = view.chat_received_message_time

    override fun drawMessage(view: MessageView) {

        if (view.from == CURRENT_UID) {
            this.blocUserMessage.visibility = View.VISIBLE
            this.blocReceivedMessage.visibility = View.GONE

            this.chatUserMessage.text = view.text
            this.chatUserMessageTime.text = view.timeStamp
                .asTime()
        } else {
            this.blocUserMessage.visibility = View.GONE
            this.blocReceivedMessage.visibility = View.VISIBLE

            this.chatReceivedMessage.text = view.text
            this.chatReceivedMessageTime.text = view.timeStamp
                .asTime()
        }
    }

    override fun onAttach(view: MessageView) {
    }

    override fun onDetach() {
    }

}