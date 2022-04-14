package com.alexaat.technical_task_android.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.alexaat.technical_task_android.model.User
import com.alexaat.technical_task_android.ui.UserLongClickEvent


class UsersAdapter: ListAdapter<User, UsersViewHolder>(CALL_BACK) {

    var longClickListener: UserLongClickEvent? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnLongClickListener {
            longClickListener?.let {
                it.onItemLongClick(item)
                true
            } ?: false
        }
    }
}

val CALL_BACK = object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}