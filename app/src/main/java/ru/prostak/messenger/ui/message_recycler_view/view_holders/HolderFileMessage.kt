package ru.prostak.messenger.ui.message_recycler_view.view_holders

import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_item_file.view.*
import ru.prostak.messenger.database.CURRENT_UID
import ru.prostak.messenger.database.getFileFromStorage
import ru.prostak.messenger.ui.message_recycler_view.views.MessageView
import ru.prostak.messenger.utilits.WRITE_FILES
import ru.prostak.messenger.utilits.asTime
import ru.prostak.messenger.utilits.checkPermissions
import ru.prostak.messenger.utilits.showToast
import java.io.File
import java.lang.Exception

class HolderFileMessage(view: View) : RecyclerView.ViewHolder(view), MessageHolder {


    private val blocUserFileMessage: ConstraintLayout = view.bloc_user_file_message
    private val chatUserFileMessageTime: TextView = view.chat_user_file_message_time

    private val blocReceivedFileMessage: ConstraintLayout = view.bloc_received_file_message
    private val chatReceivedFileMessageTime: TextView = view.chat_received_file_message_time

    private val chatUserFileName: TextView = view.chat_user_file_name
    private val chatUserBtnDownload: ImageView = view.chat_user_btn_download
    private val chatUserProgressBar: ProgressBar = view.chat_user_progress_bar

    private val chatReceivedFileName: TextView = view.chat_received_file_name
    private val chatReceivedBtnDownload: ImageView = view.chat_received_btn_download
    private val chatReceivedProgressBar: ProgressBar = view.chat_received_progress_bar


    override fun drawMessage(view: MessageView) {
        if (view.from == CURRENT_UID) {
            this.blocReceivedFileMessage.visibility = View.GONE
            this.blocUserFileMessage.visibility = View.VISIBLE

            this.chatUserFileMessageTime.text = view.timeStamp.asTime()
            this.chatUserFileName.text = view.text

        } else {
            this.blocReceivedFileMessage.visibility = View.VISIBLE
            this.blocUserFileMessage.visibility = View.GONE

            this.chatReceivedFileMessageTime.text = view.timeStamp.asTime()
            this.chatReceivedFileName.text = view.text
        }
    }

    override fun onAttach(view: MessageView) {
        if (view.from == CURRENT_UID) chatUserBtnDownload.setOnClickListener { clickToBtnFile(view) }
        else chatReceivedBtnDownload.setOnClickListener { clickToBtnFile(view) }
    }

    private fun clickToBtnFile(view: MessageView) {
        if (view.from == CURRENT_UID) {
            chatUserBtnDownload.visibility = View.INVISIBLE
            chatUserProgressBar.visibility = View.VISIBLE
        } else {
            chatReceivedBtnDownload.visibility = View.INVISIBLE
            chatReceivedProgressBar.visibility = View.VISIBLE
        }
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            view.text
        )
        try {
            if (checkPermissions(WRITE_FILES)) {
                file.createNewFile()
                getFileFromStorage(file, view.fileUrl) {
                    if (view.from == CURRENT_UID) {
                        chatUserBtnDownload.visibility = View.VISIBLE
                        chatUserProgressBar.visibility = View.INVISIBLE
                    } else {
                        chatReceivedBtnDownload.visibility = View.VISIBLE
                        chatReceivedProgressBar.visibility = View.INVISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    override fun onDetach() {
        chatUserBtnDownload.setOnClickListener(null)
        chatReceivedBtnDownload.setOnClickListener(null)
    }

}