package ru.prostak.messenger.ui.message_recycler_view.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_item_image.view.*
import ru.prostak.messenger.database.CURRENT_UID
import ru.prostak.messenger.ui.message_recycler_view.views.MessageView
import ru.prostak.messenger.utilits.asTime
import ru.prostak.messenger.utilits.downloadAndSetImage

class HolderImageMessage(view: View) : RecyclerView.ViewHolder(view), MessageHolder {

    private val blocUserImageMessage: ConstraintLayout = view.bloc_user_image_message
    private val chatUserImage: ImageView = view.chat_user_image
    private val chatUserImageMessageTime: TextView = view.chat_user_image_message_time

    private val blocReceivedImageMessage: ConstraintLayout = view.bloc_received_image_message
    private val chatReceivedImage: ImageView = view.chat_received_image
    private val chatReceivedImageMessageTime: TextView = view.chat_received_image_message_time

    override fun drawMessage(view: MessageView) {

        if (view.from == CURRENT_UID) {
            this.blocReceivedImageMessage.visibility = View.GONE
            this.blocUserImageMessage.visibility = View.VISIBLE

            this.chatUserImage.downloadAndSetImage(view.fileUrl)
            this.chatUserImageMessageTime.text = view.timeStamp.asTime()

        } else {
            this.blocReceivedImageMessage.visibility = View.VISIBLE
            this.blocUserImageMessage.visibility = View.GONE

            this.chatReceivedImage.downloadAndSetImage(view.fileUrl)
            this.chatReceivedImageMessageTime.text = view.timeStamp.asTime()
        }
    }
    override fun onAttach(view: MessageView) {
    }

    override fun onDetach() {
    }

}