package ru.prostak.messenger.ui.fragments.single_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_item.view.*
import ru.prostak.messenger.R
import ru.prostak.messenger.models.CommonModel
import ru.prostak.messenger.database.CURRENT_UID
import ru.prostak.messenger.utilits.DiffUtilCallback
import ru.prostak.messenger.utilits.asTime

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var mListMessagesCache = emptyList<CommonModel>()
    private lateinit var mDiffResult: DiffUtil.DiffResult

    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val blocUserMessage: ConstraintLayout = view.bloc_user_message
        val chatUserMessage: TextView = view.chat_user_message
        val chatUserMessageTime: TextView = view.chat_user_message_time

        val blocReceivedMessage: ConstraintLayout = view.bloc_received_message
        val chatReceivedMessage: TextView = view.chat_received_message
        val chatReceivedMessageTime: TextView = view.chat_received_message_time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.message_item, parent, false)
        return SingleChatHolder(view)

    }

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        if (mListMessagesCache[position].from == CURRENT_UID) {
            holder.blocUserMessage.visibility = View.VISIBLE
            holder.blocReceivedMessage.visibility = View.GONE
            holder.chatUserMessage.text = mListMessagesCache[position].text
            holder.chatUserMessageTime.text = mListMessagesCache[position].timeStamp
                .toString()
                .asTime()
        } else {
            holder.blocUserMessage.visibility = View.GONE
            holder.blocReceivedMessage.visibility = View.VISIBLE
            holder.chatReceivedMessage.text = mListMessagesCache[position].text
            holder.chatReceivedMessageTime.text = mListMessagesCache[position].timeStamp
                .toString()
                .asTime()
        }
    }

    override fun getItemCount(): Int = mListMessagesCache.size

    fun setList(list: List<CommonModel>) {

    }
    fun addItem(item: CommonModel){
        val newList = mutableListOf<CommonModel>()
        newList.addAll(mListMessagesCache)
        newList.add(item)
        mDiffResult = DiffUtil.calculateDiff(DiffUtilCallback(mListMessagesCache, newList))
        mDiffResult.dispatchUpdatesTo(this)
        mListMessagesCache = newList
    }
}
