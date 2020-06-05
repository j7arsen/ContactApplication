package com.j7arsen.contact.application.presentation.list.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.j7arsen.contact.application.domain.model.ContactModel

class ContactDiffUtil(private val oldList : List<ContactModel>, private val newList : List<ContactModel>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].id == oldList[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition] == oldList[oldItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        if(newItem::class == oldItem::class){
            return mutableListOf<ContactFieldState>().apply {
                if(oldItem.firstName != newItem.firstName) add(ContactFieldState.FIRST_NAME_CHANGED)
                if(oldItem.lastName != newItem.lastName) add(ContactFieldState.LAST_NAME_CHANGED)
                if(oldItem.email != newItem.email) add(ContactFieldState.EMAIL_CHANGED)
            }
        }
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    enum class ContactFieldState{
        FIRST_NAME_CHANGED, LAST_NAME_CHANGED, EMAIL_CHANGED
    }

}