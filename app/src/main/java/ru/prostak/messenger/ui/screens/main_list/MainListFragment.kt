package ru.prostak.messenger.ui.screens.main_list

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_main_list.*
import ru.prostak.messenger.R
import ru.prostak.messenger.database.*
import ru.prostak.messenger.models.CommonModel
import ru.prostak.messenger.utilits.APP_ACTIVITY
import ru.prostak.messenger.utilits.AppValueEventListener
import ru.prostak.messenger.utilits.hideKeyboard

class MainListFragment : Fragment(R.layout.fragment_main_list) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainListAdapter
    private val mRefMainList = REF_DATABASE_ROOT.child(NODE_MAIN_LIST).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
    private lateinit var mListItems: List<CommonModel>


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Чаты"
        hideKeyboard()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = main_list_recycler_view
        mAdapter = MainListAdapter()

        // 1 запрос
        mRefMainList
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
                        newModel.lastMessage = tempList[0].text
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

}