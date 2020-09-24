package ru.prostak.messenger.ui.screens.groups

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_add_contacts.*
import ru.prostak.messenger.R
import ru.prostak.messenger.database.*
import ru.prostak.messenger.models.CommonModel
import ru.prostak.messenger.ui.screens.base.BaseFragment
import ru.prostak.messenger.utilits.*

class AddContactsFragment : BaseFragment(R.layout.fragment_add_contacts) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter
    private val mRefContactsList = REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
    private lateinit var mListItems: List<CommonModel>


    override fun onResume() {
        super.onResume()
        listContacts.clear()
        APP_ACTIVITY.title = "Добавить участника"
        hideKeyboard()
        initRecyclerView()
        add_contacts_btn_next.setOnClickListener {
            if (listContacts.isEmpty()) showToast("Добавьте участников")
            else replaceFragment(CreateGroupFragment(listContacts))
        }
    }

    private fun initRecyclerView() {
        mRecyclerView = add_contacts_recycler_view
        mAdapter = AddContactsAdapter()

        // 1 запрос
        mRefContactsList
            .addListenerForSingleValueEvent(AppValueEventListener {dataSnapshot1 ->
            mListItems = dataSnapshot1
                .children
                .map { it.getCommonModel() }
            mListItems.forEach { model ->

                //2 запрос
                mRefUsers
                    .child(model.id)
                    .addListenerForSingleValueEvent(AppValueEventListener {dataSnapshot2 ->
                    val newModel = dataSnapshot2.getCommonModel()

                    //3 запрос
                    mRefMessages
                        .child(model.id)
                        .limitToLast(1)
                        .addListenerForSingleValueEvent(AppValueEventListener{ dataSnapshot3 ->
                        val tempList = dataSnapshot3.children.map { it.getCommonModel() }
                            if (tempList.isEmpty()){
                                newModel.lastMessage = "Чат очищен"
                            } else {
                                newModel.lastMessage = tempList[0].text

                            }
                            if (newModel.fullname.isEmpty()) {
                                newModel.fullname = newModel.phone
                            }
                        mAdapter.updateListItems(newModel)
                    })
                })
            }
        })
        mRecyclerView.adapter = mAdapter


    }

    companion object {
        val listContacts = mutableListOf<CommonModel>()
    }

}