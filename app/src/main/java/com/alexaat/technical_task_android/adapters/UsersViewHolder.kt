package com.alexaat.technical_task_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexaat.technical_task_android.R
import com.alexaat.technical_task_android.model.User

class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val nameTextView = itemView.findViewById<TextView>(R.id.item_view_name_text_view)
    private val emailTextView = itemView.findViewById<TextView>(R.id.item_view_email_text_view)
    private val createdTextView = itemView.findViewById<TextView>(R.id.item_view_created_text_view)

    private val context = itemView.context

    fun bind(user: User){
        nameTextView.text = user.name
        emailTextView.text = user.email
        createdTextView.text = context.resources.getString(R.string.created, user.id.toString())
    }

    companion object {
        fun create(parent: ViewGroup): UsersViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
            return UsersViewHolder(view)
        }
    }

}