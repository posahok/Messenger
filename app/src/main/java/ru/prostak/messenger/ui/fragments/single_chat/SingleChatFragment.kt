package ru.prostak.messenger.ui.fragments.single_chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*
import kotlinx.android.synthetic.main.toolbar_info.view.*
import ru.prostak.messenger.R
import ru.prostak.messenger.database.*
import ru.prostak.messenger.models.CommonModel
import ru.prostak.messenger.models.UserModel
import ru.prostak.messenger.ui.fragments.BaseFragment
import ru.prostak.messenger.utilits.*

class SingleChatFragment(private val contact: CommonModel) :
    BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var mListenerInfoToolbar: AppValueEventListener
    private lateinit var mReceivingUser: UserModel
    private lateinit var mToolbarInfo: View
    private lateinit var mRefUser: DatabaseReference
    private lateinit var mRefMessages: DatabaseReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesListener: ChildEventListener
    private var mListMessages = mutableListOf<CommonModel>()

    override fun onResume() {
        super.onResume()
        initToolbar()
        chat_btn_send_message.setOnClickListener {
            val message = chat_input_message.text.toString()
            if (message.isEmpty())
                showToast("Введите сообщение")
            else
                sendMessage(message, contact.id, TYPE_TEXT){
                    chat_input_message.setText("")
                }
        }
        initRecyclerView()

    }

    private fun initRecyclerView() {
        mRecyclerView = chat_recyclerView
        mAdapter = SingleChatAdapter()
        mRefMessages = REF_DATABASE_ROOT
            .child(NODE_MESSAGES)
            .child(CURRENT_UID)
            .child(contact.id)
        mRecyclerView.adapter = mAdapter
        mMessagesListener = AppChildEventListener{
            mAdapter.addItem(it.getCommonModel())
            mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
        }

        mRefMessages.addChildEventListener(mMessagesListener)
    }

    private fun initToolbar() {
        mToolbarInfo = APP_ACTIVITY.mToolbar.toolbar_info
        mToolbarInfo.visibility = View.VISIBLE
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUser = it.getUserModel()
            initInfoToolbar()
        }
        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
    }


    private fun initInfoToolbar() {
        if (mReceivingUser.fullname.isEmpty())
            mToolbarInfo.toolbar_chat_fullname.text = contact.fullname
        else
            mToolbarInfo.toolbar_chat_fullname.text = mReceivingUser.fullname

        mToolbarInfo.toolbar_chat_image.downloadAndSetImage(mReceivingUser.photoUrl)
        mToolbarInfo.toolbar_chat_status.text = mReceivingUser.state

    }

    override fun onPause() {
        super.onPause()
        mToolbarInfo.visibility = View.GONE
        mRefUser.removeEventListener(mListenerInfoToolbar)
        mRefMessages.removeEventListener(mMessagesListener)

    }
}