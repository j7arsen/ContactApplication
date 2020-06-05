package com.j7arsen.contact.application.presentation.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.j7arsen.contact.application.R
import com.j7arsen.contact.application.domain.model.ContactModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class ContactListAdapter(
    val onDeleteClick: ((ContactModel) -> Unit)?,
    val onItemCLick: ((Long) -> Unit)?
) : RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {

    private val contactList = mutableListOf<ContactModel>()

    fun setData(data: List<ContactModel>) {
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(ContactDiffUtil(newList = data, oldList = contactList))
        diffResult.dispatchUpdatesTo(this)
        contactList.clear()
        contactList.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contactModel = contactList[position]
        with(holder) {
            if (!contactModel.photo.isNullOrEmpty()) {
                Picasso.get()
                    .load(contactModel.photo!!)
                    .transform(CropCircleTransformation())
                    .placeholder(android.R.drawable.ic_dialog_info)
                    .error(android.R.drawable.ic_dialog_info)
                    .into(ivContactPhoto)
            } else {
                ivContactPhoto.setImageResource(android.R.drawable.ic_dialog_info)
            }
            tvContactName.text = "${contactModel.lastName} ${contactModel.firstName}"
            tvContactEmail.text = contactModel.email
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val contactModel = contactList[position]
            val currentPayloads = payloads[0] as List<ContactDiffUtil.ContactFieldState>
            currentPayloads.forEach {
                when (it) {
                    ContactDiffUtil.ContactFieldState.FIRST_NAME_CHANGED -> {
                        holder.tvContactName.text =
                            "${contactModel.lastName} ${contactModel.firstName}"
                    }
                    ContactDiffUtil.ContactFieldState.LAST_NAME_CHANGED -> {
                        holder.tvContactName.text =
                            "${contactModel.lastName} ${contactModel.firstName}"
                    }
                    ContactDiffUtil.ContactFieldState.EMAIL_CHANGED -> {
                        holder.tvContactEmail.text = contactModel.email
                    }
                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val btnContactDelete: Button = itemView.findViewById(R.id.btn_contact_delete)

        val ivContactPhoto: ImageView = itemView.findViewById(R.id.iv_contact_img)
        val tvContactName: TextView = itemView.findViewById(R.id.tv_contact_name)
        val tvContactEmail: TextView = itemView.findViewById(R.id.tv_contact_email)

        init {
            itemView.setOnClickListener { onItemCLick?.invoke(contactList[adapterPosition].id) }
            btnContactDelete.setOnClickListener { onDeleteClick?.invoke(contactList[adapterPosition]) }
        }

    }

}