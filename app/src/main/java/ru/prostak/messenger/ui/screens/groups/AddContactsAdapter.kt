package ru.prostak.messenger.ui.screens.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.add_contacts_item.view.*
import kotlinx.android.synthetic.main.main_list_item.view.*
import kotlinx.android.synthetic.main.main_list_item.view.main_list_item_name
import ru.prostak.messenger.R
import ru.prostak.messenger.models.CommonModel
import ru.prostak.messenger.ui.screens.single_chat.SingleChatFragment
import ru.prostak.messenger.utilits.downloadAndSetImage
import ru.prostak.messenger.utilits.replaceFragment
import ru.prostak.messenger.utilits.showToast

class AddContactsAdapter : RecyclerView.Adapter<AddContactsAdapter.AddContactsHolder>() {

    val listItems = mutableListOf<CommonModel>()

    class AddContactsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.add_contacts_item_name
        val itemLastMessage: TextView = view.add_contacts_last_message
        val itemPhoto: CircleImageView = view.add_contacts_item_photo
        val itemChoice: CircleImageView = view.add_contacts_item_choice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddContactsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_contacts_item, parent, false)

        val holder = AddContactsHolder(view)
        holder.itemView.setOnClickListener {
            if (listItems[holder.adapterPosition].choice){
                holder.itemChoice.visibility = View.INVISIBLE
                listItems[holder.adapterPosition].choice = false
                AddContactsFragment.listContacts.remove(listItems[holder.adapterPosition])
            } else {
                holder.itemChoice.visibility = View.VISIBLE
                listItems[holder.adapterPosition].choice = true
                AddContactsFragment.listContacts.add(listItems[holder.adapterPosition])
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: AddContactsHolder, position: Int) {
        holder.itemName.text = listItems[position].fullname
        holder.itemLastMessage.text = listItems[position].lastMessage
        holder.itemPhoto.downloadAndSetImage(listItems[position].photoUrl)
    }

    fun updateListItems(item: CommonModel) {
        listItems.add(item)
        notifyItemInserted(listItems.size)
    }

    override fun getItemCount(): Int = listItems.size
}